package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.cabecalho.CabecalhoDTO;
import com.veigadealmeida.projetofinal.dto.cabecalho.CabecalhoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.services.CabecalhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/cabecalho")
public class CabecalhoController {
    private final CabecalhoService cabecalhoService;

    public CabecalhoController(CabecalhoService cabecalhoService) {
        this.cabecalhoService = cabecalhoService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro do pré-projeto", description = "Realiza o cadastro do pré-projeto (Cabeçalho)", tags = {"CabecalhoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<CabecalhoDetalhamentoDTO> cadastrarCabecalho(@RequestBody @Valid CabecalhoDTO cabecalhoDTO, UriComponentsBuilder uriComponentsBuilder) {
        CabecalhoDetalhamentoDTO cabecalhoDetalhamentoDTO = cabecalhoService.cadastrarCabecalho(cabecalhoDTO);
        var uri = uriComponentsBuilder.path("/cabecalho/cadastrar/{id}").buildAndExpand(cabecalhoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(cabecalhoDetalhamentoDTO);
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista do pré-projeto", description = "Realiza a listagem paginada do pré-projeto (Cabeçalho)", tags = {"CabecalhoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<CabecalhoDetalhamentoDTO>> listarCabecalho(Pageable paginacao){
        paginacao = Pageable.unpaged();
        var page = cabecalhoService.listarCabecalho(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Listagem por Id", description = "Realiza listagem do Pre Projeto pelo id", tags = {"CabecalhoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<CabecalhoDetalhamentoDTO> listarCabecalhoById(@PathVariable Long id){
        CabecalhoDetalhamentoDTO cabecalhoDetalhamentoDTO = cabecalhoService.listarPorId(id);
        return ResponseEntity.ok(cabecalhoDetalhamentoDTO);
    }

}
