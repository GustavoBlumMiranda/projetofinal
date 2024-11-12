package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.configuration.utils.CreatedResources;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.dto.PerguntaOpcoesRespostasDTO;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDTO;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaEmUsoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.perguntaetapa.EditarPerguntaDTO;
import com.veigadealmeida.projetofinal.services.OpcaoRespostaService;
import com.veigadealmeida.projetofinal.services.TemplatePerguntaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/pergunta")
public class PerguntaController {

    private final TemplatePerguntaService templatePerguntaService;
    private final OpcaoRespostaService opcaoRespostaService;

    public PerguntaController(TemplatePerguntaService templatePerguntaService, OpcaoRespostaService opcaoRespostaService){
        this.templatePerguntaService = templatePerguntaService;
        this.opcaoRespostaService = opcaoRespostaService;
    }

    @PostMapping("/cadastrar")
    @Transactional
    @Operation(summary = "Cadastro do Template Pergunta", description = "Realiza o cadastro de um Template de Pergunta (Nome e Tipo)", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<PerguntaDetalhamentoDTO> cadastrarPergunta(@RequestBody @Valid PerguntaDTO perguntaDTO, UriComponentsBuilder uriComponentsBuilder){
        PerguntaDetalhamentoDTO perguntaDetalhamentoDTO = templatePerguntaService.cadastrarPergunta(perguntaDTO);
        var uri = uriComponentsBuilder.path("/pergunta/cadastrar/{id}").buildAndExpand(perguntaDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(perguntaDetalhamentoDTO);
    }

    @PutMapping("/{id}/ativo")
    @Operation(summary = "Alteração de Estado da pergunta", description = "Ativa/Desativa um Template de Pergunta", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Pergunta> habilitarPergunta(@PathVariable Long id){
            Pergunta pergunta = templatePerguntaService.habilitarPergunta(id);
            return ResponseEntity.ok(pergunta);
    }

    @PostMapping("/cadastraropcoes")
    @Operation(summary = "Cadastrar Opçõoes Resposta", description = "Cadastro de uma lista de Opções Resposta (tabela opção_resposta)", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<CreatedResources<OpcaoRespostaDetalhamentoDTO>> cadastrarOpcaoResposta(@RequestBody @Valid List<OpcaoRespostaDTO> opcaoRespostaDTOList, UriComponentsBuilder uriComponentsBuilder){
        List<OpcaoRespostaDetalhamentoDTO> ordDTOList = opcaoRespostaService.cadastrarOpcoesRespostas(opcaoRespostaDTOList);
        List<URI> uris = new ArrayList<>();
        for (OpcaoRespostaDetalhamentoDTO ordDTO : ordDTOList) {
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/pergunta/cadastraropcoes/{id}")
                    .buildAndExpand(ordDTO.id())
                    .toUri();
            uris.add(uri);
        }
        CreatedResources<OpcaoRespostaDetalhamentoDTO> createdResources = new CreatedResources<>(ordDTOList, uris);
        return ResponseEntity.created(createdResources.uris().get(0)).body(createdResources);
    }

    @PutMapping("/{id}/editar")
    @Operation(summary = "Edição das Perguntas", description = "Permite Edição das perguntas e das opções respostas (caso seja multipla escolha)", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<PerguntaOpcoesRespostasDTO> editarPergunta(
            @PathVariable Long id,
            @RequestBody @Valid EditarPerguntaDTO editarPerguntaDTO,
            UriComponentsBuilder uriComponentsBuilder) {

        PerguntaDetalhamentoDTO perguntaDetalhamentoDTO = templatePerguntaService.editarPergunta(id, editarPerguntaDTO.perguntaDTO());

        List<OpcaoRespostaDetalhamentoDTO> ordDTOList = Collections.emptyList();
        if (editarPerguntaDTO.opcaoRespostaDetalhamentoDTOList() != null && !editarPerguntaDTO.opcaoRespostaDetalhamentoDTOList().isEmpty()) {
            ordDTOList = opcaoRespostaService.editarOpcoesRespostas(editarPerguntaDTO.opcaoRespostaDetalhamentoDTOList());
        }

        PerguntaOpcoesRespostasDTO perguntaOpcoesRespostasDTO = new PerguntaOpcoesRespostasDTO(perguntaDetalhamentoDTO, ordDTOList);

        UriComponents uriComponents = uriComponentsBuilder.path("/{id}")
                .buildAndExpand(perguntaOpcoesRespostasDTO.perguntaDetalhamentoDTO().id());

        return ResponseEntity.ok()
                .location(uriComponents.toUri())
                .body(perguntaOpcoesRespostasDTO);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listagem das Perguntas", description = "Realiza a listagem paginada das perguntas existentes", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<PerguntaDetalhamentoDTO>> listarPergunta(@PageableDefault(sort = {"id"}) Pageable paginacao){
        paginacao = Pageable.unpaged();
        var page = templatePerguntaService.listarPerguntas(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/listarportemplateetapa/{id}")
    @Operation(summary = "Listagem por TemplateEtapa", description = "Realiza listagem das perguntas existentes por TemplateEtapa definido", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<PerguntaDetalhamentoDTO>> listarPerguntaPorTemplateEtapa( @PathVariable Long id){
        List<PerguntaDetalhamentoDTO> listaPerguntaDetalhamentoDTO = templatePerguntaService.listarPeruntasPorTemplateEtapa(id);
        return ResponseEntity.ok(listaPerguntaDetalhamentoDTO);
    }

    @GetMapping("/listarporetapa/{idetapa}/projeto/{idprojeto}")
    @Operation(summary = "Listagem por Etapa e projeto", description = "Realiza listagem das perguntas existentes por Etapa e projeto definidos", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<PerguntaEmUsoDetalhamentoDTO>> listarPerguntaPorEtapa(@PathVariable Long idetapa, @PathVariable Long idprojeto){
        List<PerguntaEmUsoDetalhamentoDTO> listaPerguntaDetalhamentoDTO = templatePerguntaService.listarPerguntasPorEtapa(idetapa, idprojeto);
        return ResponseEntity.ok(listaPerguntaDetalhamentoDTO);
    }


}
