package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.cabecalho.ConsultaCep;
import com.veigadealmeida.projetofinal.dto.cabecalho.EnderecoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/endereco")
public class EnderecoController {
    private final ConsultaCep consultaCep;

    public EnderecoController(ConsultaCep consultaCep) {
        this.consultaCep = consultaCep;
    }

    @GetMapping("/{cep}")
    @Operation(summary = "Busca um endereço", description = "Realiza a busca de um endereço pelo CEP", tags = {"EnderecoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable String cep) {
        EnderecoDTO enderecoDTO = consultaCep.buscaEndereco(cep);
        return ResponseEntity.ok(enderecoDTO);
    }
}
