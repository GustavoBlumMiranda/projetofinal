package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.etapa.*;
import com.veigadealmeida.projetofinal.dto.projeto.AlteraProjetoDTO;
import com.veigadealmeida.projetofinal.dto.projeto.ProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.services.EtapaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@CrossOrigin
@RequestMapping("/etapa")
public class EtapaController {


    private final EtapaService etapaService;

    public EtapaController(EtapaService etapaService){
        this.etapaService = etapaService;

    }

    /*@PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de Etapa", description = "Realiza o cadastro de uma Etapa com uma lista Perguntas", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<EtapaDetalhamentoDTO> cadastrarEtapa(@RequestBody @Valid EtapaCadastroDTO etapaCadastroDTO, UriComponentsBuilder uriComponentsBuilder){
        EtapaDetalhamentoDTO etapaDetalhamentoDTO = etapaService.cadastrarEtapa(etapaCadastroDTO);
        var uri = uriComponentsBuilder.path("/etapa/cadastrar/{id}").buildAndExpand(etapaDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(etapaDetalhamentoDTO);
    }*/

    @GetMapping("/listar")
    @Operation(summary = "Listagem das Etapa", description = "Realiza a listagem paginada das etapas existentes", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<EtapaDetalhamentoDTO>> listarProjetos(@PageableDefault(sort = {"id"}) Pageable paginacao){
        var page = etapaService.listarEtapas(paginacao);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar uma Etapa", description = "Realiza a busca de uma etapa por ID", tags = {"EtapaController"})
    public ResponseEntity<EtapaDetalhamentoDTO> buscaProjetoPorId(@PathVariable(value = "id") long id){
        EtapaDetalhamentoDTO etapaDetalhamentoDTO = etapaService.buscar(id);
        return ResponseEntity.ok(etapaDetalhamentoDTO);
    }

    @PutMapping("/editar")
    @Operation(summary = "Altera dados de uma Etapa", description = "Altera dados de uma etapa", tags = {"EtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<EtapaDetalhamentoDTO> editarEtapa(@RequestBody AlteraEtapaDTO alteraEtapaDTO){
        EtapaDetalhamentoDTO etapaDetalhamentoDTO = etapaService.editarEtapa(alteraEtapaDTO);
        return ResponseEntity.ok(etapaDetalhamentoDTO);
    }



}
