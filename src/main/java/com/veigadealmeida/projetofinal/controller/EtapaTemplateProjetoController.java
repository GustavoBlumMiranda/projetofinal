package com.veigadealmeida.projetofinal.controller;


import com.veigadealmeida.projetofinal.domain.EtapaTemplateProjeto;
import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.AlterarOrdemEtapaTemplateProjetoDTO;
import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.EtapaTemplateProjetoDTO;
import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.EtapaTemplateProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.services.EtapaTemplateProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/etapatemplateprojeto")
public class EtapaTemplateProjetoController {

    private final EtapaTemplateProjetoService etapaTemplateProjetoService;

    public EtapaTemplateProjetoController(EtapaTemplateProjetoService etapaTemplateProjetoService){
        this.etapaTemplateProjetoService = etapaTemplateProjetoService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de Template Etapa em um Template Projeto", description = "Realiza o cadastro de um Template de Etapa em um Template Projeto (Tabela etapa_template_projeto)", tags = {"EtapaTemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<EtapaTemplateProjetoDetalhamentoDTO> cadastrarEtapaTemplateProjeto(@RequestBody @Valid EtapaTemplateProjetoDTO etapaTemplateProjetoDTO, UriComponentsBuilder uriComponentsBuilder){
        EtapaTemplateProjetoDetalhamentoDTO etapaTemplateProjetoDetalhamentoDTO = etapaTemplateProjetoService.cadastrarEtapaTemplateProjetoDetalhamento(etapaTemplateProjetoDTO);
        var uri = uriComponentsBuilder.path("/etapatemplateprojeto/cadastrar/{id}").buildAndExpand(etapaTemplateProjetoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(etapaTemplateProjetoDetalhamentoDTO);
    }

    @PutMapping("/alterarOrdem")
    @Operation(summary = "Altera a Ordem da Etapa", description = "Realiza a alteração da ordem do Template Etapa dentro de um Template Projeto", tags = {"EtapaTemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity alterarOrdemEtapaTemplateProjeto(@RequestBody @Valid List<AlterarOrdemEtapaTemplateProjetoDTO> listaAlterarOrdemEtapaTemplateProjetoDTO){
        List<Optional<EtapaTemplateProjeto>> etapaTemplateProjetos = etapaTemplateProjetoService.alterarOrdemEtapaTemplateProjeto(listaAlterarOrdemEtapaTemplateProjetoDTO);
        return ResponseEntity.ok(etapaTemplateProjetos);
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista de EtapaTemplateProjeto", description = "Realiza a listagem paginada de Etapas em Template Projeto (Tabela etapa_template_projeto)", tags = {"EtapaTemplateProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<EtapaTemplateProjetoDetalhamentoDTO>> listarEtapaTemplateProjeto(@PageableDefault(sort = {"id"}) Pageable paginacao){
        var page = etapaTemplateProjetoService.listarEtapaTemplateProjeto(paginacao);
        return ResponseEntity.ok(page);
    }

}
