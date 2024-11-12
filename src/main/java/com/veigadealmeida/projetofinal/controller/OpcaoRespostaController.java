package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDTO;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.OpcaoRespostaRepository;
import com.veigadealmeida.projetofinal.repository.PerguntaRepository;
import com.veigadealmeida.projetofinal.services.OpcaoRespostaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/opcaoresposta")
public class OpcaoRespostaController {

    private final OpcaoRespostaRepository opcaoRespostaRepository;
    private final PerguntaRepository perguntaRepository;
    private final OpcaoRespostaService opcaoRespostaService;

    public OpcaoRespostaController(OpcaoRespostaRepository opcaoRespostaRepository, PerguntaRepository perguntaRepository, OpcaoRespostaService opcaoRespostaService){
        this.opcaoRespostaRepository = opcaoRespostaRepository;
        this.perguntaRepository = perguntaRepository;
        this.opcaoRespostaService = opcaoRespostaService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de OpcaoResposta", description = "Realiza o cadastro de OpcaoResposta", tags = {"OpcaoRespostaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<OpcaoRespostaDetalhamentoDTO> cadastrarOpcaoResposta(@RequestBody @Valid OpcaoRespostaDTO opcaoRespostaDTO, UriComponentsBuilder uriComponentsBuilder){
        OpcaoRespostaDetalhamentoDTO opcaoRespostaDetalhamentoDTO = opcaoRespostaService.cadastrarOpcaoResposta(opcaoRespostaDTO);
        var uri = uriComponentsBuilder.path("/usuario/cadastrar/{id}").buildAndExpand(opcaoRespostaDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(opcaoRespostaDetalhamentoDTO);
    }

}
