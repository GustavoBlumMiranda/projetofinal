package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.cargo.CargoDTO;
import com.veigadealmeida.projetofinal.dto.cargo.CargoDetalhamentoDTO;
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

    @PostMapping("/cadastrar")
    @Transactional
    @Operation(summary = "Cadastro do cargo", description = "Realiza o cadastro do cargo", tags = {"CargoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<CargoDetalhamentoDTO> cadastrarCargo(@RequestBody @Valid CargoDTO cargoDTO, UriComponentsBuilder uriComponentsBuilder){
        CargoDetalhamentoDTO cargoDetalhamentoDTO = cargoService.cadastrarCargo(cargoDTO);
        var uri = uriComponentsBuilder.path("/cargo/cadastrar/{id}").buildAndExpand(cargoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(cargoDetalhamentoDTO);
    }

}
