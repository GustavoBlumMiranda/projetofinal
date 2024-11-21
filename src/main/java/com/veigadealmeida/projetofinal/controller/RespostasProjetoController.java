package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.respostasprojeto.*;
import com.veigadealmeida.projetofinal.services.RespostasProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/respostasprojeto")
public class RespostasProjetoController {
    private final RespostasProjetoService respostasProjetoService;

    public RespostasProjetoController(RespostasProjetoService respostasProjetoService) {
        this.respostasProjetoService = respostasProjetoService;
    }

    @GetMapping("/buscaresposta/{id}")
    @Operation(summary = "Buscar Resposta por ID", description = "Busca os detalhes de uma resposta por ID.", tags = {"RespostasProjetoController"})
    public ResponseEntity<RespostaPerguntaDetalhamentoDTO> buscaPorId(@PathVariable(value = "id") Long id) {
        RespostaPerguntaDetalhamentoDTO respostaDetalhamento = respostasProjetoService.buscaPorId(id);
        return ResponseEntity.ok(respostaDetalhamento);
    }

    @GetMapping("/buscaretapa/{idEtapaEmUso}")
    @Operation(summary = "Buscar Respostas por Etapa", description = "Busca as respostas associadas a uma etapa em uso.", tags = {"RespostasProjetoController"})
    public ResponseEntity<RespostasEtapaDetalhamentoDTO> buscaPorEtapaEmUso(@PathVariable(value = "idEtapaEmUso") Long idEtapaEmUso) {
        RespostasEtapaDetalhamentoDTO respostasEtapaDetalhamento = respostasProjetoService.buscaPorEtapaEmUso(idEtapaEmUso);
        return ResponseEntity.ok(respostasEtapaDetalhamento);
    }

    @GetMapping("/buscarprojeto/{idUsuario}/{idProjeto}")
    @Operation(summary = "Buscar Respostas por Projeto e Usuário", description = "Busca todas as respostas associadas a um projeto de um determinado usuário.", tags = {"RespostasProjetoController"})
    public ResponseEntity<RespostasProjetoDetalhamentoDTO> buscaPorProjetoUsuario(
            @PathVariable(value = "idUsuario") Long idUsuario,
            @PathVariable(value = "idProjeto") Long idProjeto) {
        RespostasProjetoDetalhamentoDTO respostasProjetoDetalhamento = respostasProjetoService.buscaPorProjetoUsuario(idUsuario, idProjeto);
        return ResponseEntity.ok(respostasProjetoDetalhamento);
    }
}
