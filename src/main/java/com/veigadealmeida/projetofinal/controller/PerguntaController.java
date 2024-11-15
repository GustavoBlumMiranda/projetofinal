package com.veigadealmeida.projetofinal.controller;


import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.services.OpcaoRespostaService;
import com.veigadealmeida.projetofinal.services.PerguntaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/pergunta")
public class PerguntaController {

    private final PerguntaService perguntaService;
    private final OpcaoRespostaService opcaoRespostaService;

    public PerguntaController(PerguntaService perguntaService, OpcaoRespostaService opcaoRespostaService){
        this.perguntaService = perguntaService;
        this.opcaoRespostaService = opcaoRespostaService;
    }

    @PostMapping("/cadastrar")
    @Transactional
    @Operation(summary = "Cadastro de Pergunta", description = "Realiza o cadastro de uma nova Pergunta", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<PerguntaDetalhamentoDTO> cadastrarPergunta(@RequestBody @Valid PerguntaDTO perguntaDTO, UriComponentsBuilder uriComponentsBuilder) {
        PerguntaDetalhamentoDTO perguntaDetalhamentoDTO = perguntaService.cadastrarPergunta(perguntaDTO);
        var uri = uriComponentsBuilder.path("/pergunta/{id}").buildAndExpand(perguntaDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(perguntaDetalhamentoDTO);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar Perguntas", description = "Retorna uma lista paginada de todas as Perguntas", tags = {"PerguntaController"})
    public ResponseEntity<Page<PerguntaDetalhamentoDTO>> listarPerguntas(Pageable paginacao) {
        Page<PerguntaDetalhamentoDTO> perguntas = perguntaService.listarPerguntas(paginacao);
        return ResponseEntity.ok(perguntas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Pergunta", description = "Retorna os detalhes de uma Pergunta pelo ID", tags = {"PerguntaController"})
    public ResponseEntity<PerguntaDetalhamentoDTO> buscaPergunta(@PathVariable Long id) {
        PerguntaDetalhamentoDTO perguntaDetalhamentoDTO = perguntaService.buscaPergunta(id);
        return ResponseEntity.ok(perguntaDetalhamentoDTO);
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    @Operation(summary = "Atualizar Pergunta", description = "Atualiza os detalhes de uma Pergunta pelo ID", tags = {"PerguntaController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<PerguntaDetalhamentoDTO> atualizarPergunta(@PathVariable Long id, @RequestBody @Valid PerguntaDTO perguntaDTO) {
        PerguntaDetalhamentoDTO perguntaDetalhamentoDTO = perguntaService.atualizarPergunta(id, perguntaDTO);
        return ResponseEntity.ok(perguntaDetalhamentoDTO);
    }


}
