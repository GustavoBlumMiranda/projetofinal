package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.perguntaetapa.AssociaPerguntaEtapaDTO;
import com.veigadealmeida.projetofinal.dto.perguntaetapa.PerguntaEtapaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.services.PerguntaEtapaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/perguntaetapa")
public class PerguntaEtapaController {

    private PerguntaEtapaService perguntaEtapaService;

    public PerguntaEtapaController(PerguntaEtapaService perguntaEtapaService){
        this.perguntaEtapaService = perguntaEtapaService;

    }

   /* @PostMapping("/associar")
    @Transactional
    @Operation(summary = "Associação Pergunta > Etapa", description = "Realiza associação de Perguntas a Etapas (tabela pergunta_etapa)", tags = {"PerguntaEtapaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<PerguntaEtapaDetalhamentoDTO> associaPerguntaEtapa(@RequestBody @Valid AssociaPerguntaEtapaDTO associaPerguntaEtapaDTO, UriComponentsBuilder uriComponentsBuilder){
        PerguntaEtapaDetalhamentoDTO perguntaEtapaDetalhamentoDTO = perguntaEtapaService.associaPerguntaEtapa(associaPerguntaEtapaDTO);
        var uri = uriComponentsBuilder.path("/perguntaetapa/associar/{id}").buildAndExpand(perguntaEtapaDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(perguntaEtapaDetalhamentoDTO);
    }*/

}
