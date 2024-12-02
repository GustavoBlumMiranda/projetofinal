package com.veigadealmeida.projetofinal.controller;



import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.RespostaPerguntaDTO;
import com.veigadealmeida.projetofinal.services.PerguntaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pergunta")
public class PerguntaController {

    private final PerguntaService perguntaService;

    public PerguntaController(PerguntaService perguntaService){
        this.perguntaService = perguntaService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar Perguntas", description = "Retorna uma lista paginada de todas as Perguntas", tags = {"PerguntaController"})
    public ResponseEntity<Page<PerguntaDetalhamentoDTO>> listarPerguntas(@PageableDefault(sort = {"id"}, size = 200) Pageable paginacao) {
        Page<PerguntaDetalhamentoDTO> perguntas = perguntaService.listarPerguntas(paginacao);
        return ResponseEntity.ok(perguntas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Pergunta", description = "Retorna os detalhes de uma Pergunta pelo ID", tags = {"PerguntaController"})
    public ResponseEntity<PerguntaDetalhamentoDTO> buscaPergunta(@PathVariable Long id) {
        PerguntaDetalhamentoDTO perguntaDetalhamentoDTO = perguntaService.buscaPergunta(id);
        return ResponseEntity.ok(perguntaDetalhamentoDTO);
    }


    @PostMapping("/responder")
    @Operation(summary = "Responde uma pergunta", description = "Responde uma pergunta", tags = {"PerguntaController"})
    public ResponseEntity<String> responderPergunta(@RequestBody RespostaPerguntaDTO respostaPerguntaDTO) {
        return ResponseEntity.ok("{\"message\": \"" + perguntaService.responderPergunta(respostaPerguntaDTO) + "\"}");
    }


}
