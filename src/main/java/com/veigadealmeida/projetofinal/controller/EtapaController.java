package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.etapa.*;
import com.veigadealmeida.projetofinal.services.EtapaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/etapa")
public class EtapaController {


    private final EtapaService etapaService;

    public EtapaController(EtapaService etapaService){
        this.etapaService = etapaService;

    }

    @GetMapping("/listar")
    @Operation(summary = "Listagem das Etapa", description = "Realiza a listagem paginada das etapas existentes", tags = {"EtapaController"})
    public ResponseEntity<Page<EtapaDetalhamentoDTO>> listarEtapas(@PageableDefault(sort = {"id"}, size = 200) Pageable paginacao){
        var page = etapaService.listarEtapas(paginacao);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar uma Etapa", description = "Realiza a busca de uma etapa por ID", tags = {"EtapaController"})
    public ResponseEntity<EtapaDetalhamentoDTO> buscaEtapaPorId(@PathVariable(value = "id") long id){
        EtapaDetalhamentoDTO etapaDetalhamentoDTO = etapaService.buscar(id);
        return ResponseEntity.ok(etapaDetalhamentoDTO);
    }



}
