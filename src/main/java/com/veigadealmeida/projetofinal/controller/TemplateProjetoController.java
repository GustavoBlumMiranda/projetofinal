package com.veigadealmeida.projetofinal.controller;


import com.veigadealmeida.projetofinal.configuration.utils.CreatedResources;
import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.EtapaTemplateProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.templateprojeto.*;
import com.veigadealmeida.projetofinal.services.TemplateProjetoService;
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

@RestController
@CrossOrigin
@RequestMapping("/templateprojeto")
public class TemplateProjetoController {
    /*private final TemplateProjetoService templateProjetoService;

    public TemplateProjetoController(TemplateProjetoService templateProjetoService){
        this.templateProjetoService = templateProjetoService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de TemplateProjeto", description = "Realiza o cadastro de um TemplateProjeto", tags = {"TemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<TemplateProjetoDetalhamentoDTO> cadastrarTemplateProjeto(@RequestBody @Valid TemplateProjetoDTO templateProjetoDTO, UriComponentsBuilder uriComponentsBuilder){
        TemplateProjetoDetalhamentoDTO templateProjetoDetalhamentoDTO = templateProjetoService.cadastrarTemplateProjeto(templateProjetoDTO);
        var uri = uriComponentsBuilder.path("/templateprojeto/cadastrar/{id}").buildAndExpand(templateProjetoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(templateProjetoDetalhamentoDTO);
    }

    @PostMapping("/cadastrarcomlista")
    @Operation(summary = "Cadastro de TemplateProjeto com etapa", description = "Realiza o cadastro de um TemplateProjeto com uma associação de Template Etapas", tags = {"TemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<CreatedResources<EtapaTemplateProjetoDetalhamentoDTO>> cadastrarTemplateProjetoComListaEtapa(@RequestBody @Valid CadastroTemplateProjetoComListaEtapaDTO cadastroTemplateProjetoComListaEtapaDTO, UriComponentsBuilder uriComponentsBuilder){
        List<EtapaTemplateProjetoDetalhamentoDTO> listaEtapaTemplateProjetoDetalhamentoDTO = templateProjetoService.cadastrarTemplateProjetoComListaEtapa(cadastroTemplateProjetoComListaEtapaDTO);
        List<URI> uris = new ArrayList<>();
        for (EtapaTemplateProjetoDetalhamentoDTO etapaTemplateProjetoDetalhamentoDTO : listaEtapaTemplateProjetoDetalhamentoDTO) {
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/templateprojeto/cadastrarlista/{id}")
                    .buildAndExpand(etapaTemplateProjetoDetalhamentoDTO.id())
                    .toUri();
            uris.add(uri);
        }
        CreatedResources<EtapaTemplateProjetoDetalhamentoDTO> createdResources = new CreatedResources<>(listaEtapaTemplateProjetoDetalhamentoDTO, uris);
        return ResponseEntity.created(createdResources.uris().get(0)).body(createdResources);
    }

  *//* @PutMapping("/ativaroudesativar")
   @Operation(summary = "Alteração de Estado do TemplateProjeto", description = "Ativa/Desativa um Template de Projeto", tags = {"TemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
   public ResponseEntity ativarOuInativarTemplateProjeto(@RequestBody @Valid AtivarDesativarTemplateProjetoDTO ativarDesativarTemplateProjetoDTO){
        TemplateProjeto templateProjeto = templateProjetoService.ativarInativarTemplateProjeto(ativarDesativarTemplateProjetoDTO);
        return ResponseEntity.ok(new TemplateProjetoDetalhamentoDTO(templateProjeto));
    }*//*

   @PutMapping("/editar")
   @Operation(summary = "Edição do TemplateProjeto", description = "Permite Edição dos TemplateProjeto", tags = {"TemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
   public ResponseEntity editarTemplateProjeto(@RequestBody @Valid EditarTemplateProjetoDTO editarTemplateProjetoDTO){
       return ResponseEntity.ok(templateProjetoService.editarTemplateProjeto(editarTemplateProjetoDTO));
   }

//    @PutMapping("/editar/{id}")
//    @Operation(summary = "Edição de TemplateProjeto com etapa", description = "Realiza a edição de um TemplateProjeto com uma associação de Template Etapas", tags = {"TemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
//    public ResponseEntity<CreatedResources<EtapaTemplateProjetoDetalhamentoDTO>> editarTemplateProjetoComListaEtapa(
//            @PathVariable(value = "id") long id,
//            @RequestBody @Valid CadastroTemplateProjetoComListaEtapaDTO cadastroTemplateProjetoComListaEtapaDTO,
//            UriComponentsBuilder uriComponentsBuilder
//    ) {
//        // Verifique se o TemplateProjeto com o ID fornecido existe, caso contrário, retorne um erro 404 ou 400, se preferir.
//
//        List<EtapaTemplateProjetoDetalhamentoDTO> listaEtapaTemplateProjetoDetalhamentoDTO = templateProjetoService.editarTemplateProjetoComListaEtapa(id, cadastroTemplateProjetoComListaEtapaDTO);
//        List<URI> uris = new ArrayList<>();
//
//        for (EtapaTemplateProjetoDetalhamentoDTO etapaTemplateProjetoDetalhamentoDTO : listaEtapaTemplateProjetoDetalhamentoDTO) {
//            URI uri = uriComponentsBuilder
//                    .path("/templateprojeto/{id}/cadastrarlista/{etapaId}")
//                    .buildAndExpand(id, etapaTemplateProjetoDetalhamentoDTO.id())
//                    .toUri();
//            uris.add(uri);
//        }
//
//        CreatedResources<EtapaTemplateProjetoDetalhamentoDTO> createdResources = new CreatedResources<>(listaEtapaTemplateProjetoDetalhamentoDTO, uris);
//        return ResponseEntity.created(createdResources.uris().get(0)).body(createdResources);
//    }

   @GetMapping("/listar")
   @Operation(summary = "Listagem dos TemplateProjeto", description = "Realiza a listagem paginada dos Templates existentes", tags = {"TemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
   public ResponseEntity<Page<TemplateProjetoDetalhamentoDTO>> listarTemplatesProjetos(Pageable paginacao){
       paginacao = Pageable.unpaged();
       var page = templateProjetoService.listarTemplatesProjetos(paginacao);
       return ResponseEntity.ok(page);
   }*/

    /*@GetMapping("/listar/{id}")
    @Operation(summary = "Buscar Template de Projeto por Id", description = "Retorna um Template de Projeto baseado no IdTemplateProjeto passado", tags = {"TemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<TemplateProjetoDetalhamentoDTO> buscaTemplateProjetoPorId(@PathVariable(value = "id") long id){
        TemplateProjeto templateProjeto = templateProjetoService.getTemplateProjetoById(id, true);
        TemplateProjetoDetalhamentoDTO templateProjetoDetalhamentoDTO = new TemplateProjetoDetalhamentoDTO(templateProjeto);
        return ResponseEntity.ok(templateProjetoDetalhamentoDTO);
    }*/

}
