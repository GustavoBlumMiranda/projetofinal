package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.configuration.utils.CreatedResources;
import com.veigadealmeida.projetofinal.domain.Etapa;
import com.veigadealmeida.projetofinal.domain.PerguntaEtapa;
import com.veigadealmeida.projetofinal.dto.etapa.AtivarOuDesativarEtapaDTO;
import com.veigadealmeida.projetofinal.dto.etapa.CadastroEtapaTemplateDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EditarTituloEtapaDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaTemplateDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.perguntaetapa.AlterarOrdemPerguntaEtapaDTO;
import com.veigadealmeida.projetofinal.dto.perguntaetapa.PerguntaEtapaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.services.TemplateEtapaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/etapa")
public class EtapaController {


    private final TemplateEtapaService templateEtapaService;

    public EtapaController(TemplateEtapaService templateEtapaService){
        this.templateEtapaService = templateEtapaService;

    }

    @PostMapping("/template/cadastrar")
    @Operation(summary = "Cadastro do Template Etapa", description = "Realiza o cadastro de um Template de Etapa com uma lista de (ids) Perguntas", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<CreatedResources<PerguntaEtapaDetalhamentoDTO>> cadastrarTemplateEtapaComListaPergunta(@RequestBody @Valid CadastroEtapaTemplateDTO cadastroEtapaTemplateDTO, UriComponentsBuilder uriComponentsBuilder){
        List<PerguntaEtapaDetalhamentoDTO> listaPerguntaEtapaDetalhamentoDTO = templateEtapaService.cadastrarTemplateEtapaComListaPergunta(cadastroEtapaTemplateDTO);
        List<URI> uris = new ArrayList<>();
        for (PerguntaEtapaDetalhamentoDTO perguntaEtapaDetalhamentoDTO : listaPerguntaEtapaDetalhamentoDTO) {
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/etapa/template/cadastrar/{id}")
                    .buildAndExpand(perguntaEtapaDetalhamentoDTO.id())
                    .toUri();
            uris.add(uri);
        }
        CreatedResources<PerguntaEtapaDetalhamentoDTO> createdResources = new CreatedResources<>(listaPerguntaEtapaDetalhamentoDTO, uris);
        return ResponseEntity.created(createdResources.uris().get(0)).body(createdResources);
    }

    @PutMapping("/ativaroudesativar")
    @Operation(summary = "Mudança de estado da Etapa", description = "Ativa/Desativa um Template Etapa", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity ativarOudesativarEtapa(@RequestBody @Valid AtivarOuDesativarEtapaDTO etapaDesativarDTO){
        Etapa etapa = templateEtapaService.ativarOudesativarEtapa(etapaDesativarDTO);
        return ResponseEntity.ok(new EtapaTemplateDetalhamentoDTO(etapa));
    }

    @PutMapping("/alterarOrdemPerguntas")
    @Operation(summary = "Mudança de ordem das perguntas", description = "Altera a ordem da pergunta dentro do Template Etapa", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity alterarOrdemPerguntaEtapa(@RequestBody @Valid List<AlterarOrdemPerguntaEtapaDTO> listaAlterarOrdemPerguntaEtapaDTO){
        List<Optional<PerguntaEtapa>> perguntaEtapas = templateEtapaService.alterarOrdemPerguntaEtapa(listaAlterarOrdemPerguntaEtapaDTO);
        return ResponseEntity.ok(perguntaEtapas);
    }

    @PutMapping("/editartitulo")
    @Operation(summary = "Alterar Titulo da Etapa", description = "Edição do titulo do Template Etapa", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity editarTituloEtapa(@RequestBody @Valid EditarTituloEtapaDTO editarTituloEtapaDTO){
        EtapaTemplateDetalhamentoDTO etapaTemplateDetalhamentoDTO = templateEtapaService.editarTituloEtapa(editarTituloEtapaDTO);
        return ResponseEntity.ok(etapaTemplateDetalhamentoDTO);
    }

    @GetMapping("/template/listar")
    @Operation(summary = "Lista de Etapas", description = "Realiza a listagem paginada de Etapa", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<EditarTituloEtapaDTO>> listarEtapas(Pageable paginacao){
        paginacao = Pageable.unpaged();
        var page = templateEtapaService.listarEtapas(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/template/listarportemplateprojeto/{id}")
    @Operation(summary = "Listagem de Etapas por TemplateProjeto", description = "Realiza a listagem de Etapas existentes em um Template de Projeto", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<EtapaTemplateDetalhamentoDTO>> listarEtapasPorTemplateProjeto(@PathVariable Long id){
        List<EtapaTemplateDetalhamentoDTO> listaEtapaTemplateDetalhamentoDTO  = templateEtapaService.listaTemplateEtapasPorTemplateProjeto(id);
        return ResponseEntity.ok(listaEtapaTemplateDetalhamentoDTO);
    }

    @GetMapping("/template/listarporprojeto/{id}")
    @Operation(summary = "Listagem de Etapas por Projeto", description = "Realiza a listagem de Etapas existentes em um Projeto", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<EtapaTemplateDetalhamentoDTO>> listarEtapasPorProjeto(@PathVariable  Long id){
        List<EtapaTemplateDetalhamentoDTO> listaEtapaTemplateDetalhamentoDTO  = templateEtapaService.listaTemplateEtapasPorProjeto(id);
        return ResponseEntity.ok(listaEtapaTemplateDetalhamentoDTO);
    }

}
