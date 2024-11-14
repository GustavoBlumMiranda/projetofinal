package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.grupo.GrupoDTO;
import com.veigadealmeida.projetofinal.dto.grupo.GrupoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.services.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/cargo")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService){
        this.cargoService = cargoService;
    }

    /*@PostMapping("/cadastrar")
    @Transactional
    @Operation(summary = "Cadastro do cargo", description = "Realiza o cadastro do cargo", tags = {"CargoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<GrupoDetalhamentoDTO> cadastrarCargo(@RequestBody @Valid GrupoDTO grupoDTO, UriComponentsBuilder uriComponentsBuilder){
        GrupoDetalhamentoDTO grupoDetalhamentoDTO = cargoService.cadastrarCargo(grupoDTO);
        var uri = uriComponentsBuilder.path("/cargo/cadastrar/{id}").buildAndExpand(grupoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(grupoDetalhamentoDTO);
    }*/

}
