package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.domain.RespostasProjeto;
import com.veigadealmeida.projetofinal.dto.respostasprojeto.*;
import com.veigadealmeida.projetofinal.services.RespostasProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/respostasprojeto")
public class RespostasProjetoController {
    private final RespostasProjetoService respostasProjetoService;

    public RespostasProjetoController(RespostasProjetoService respostasProjetoService){
        this.respostasProjetoService = respostasProjetoService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de Resposta Projeto", description = "Realiza o cadastro de uma Resposta dentro de um Projeto (Tabela respostas_projeto)", tags = {"RespostaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<RespostasProjetoDetalhamentoDTO> cadastrarRespostasProjeto(@RequestBody @Valid RespostasProjetoDTO respostasProjetoDTO, UriComponentsBuilder uriComponentsBuilder){
        RespostasProjetoDetalhamentoDTO respostasProjetoDetalhamentoDTO = respostasProjetoService.cadastrarRespostasProjeto(respostasProjetoDTO);
        var uri = uriComponentsBuilder.path("/respostasprojeto/cadastrar/{id}").buildAndExpand(respostasProjetoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(respostasProjetoDetalhamentoDTO);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listagem das Respostas Projetos", description = "Realiza a listagem paginada dos respostas em projetos existentes", tags = {"RespostaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<RespostasProjetoDetalhamentoDTO>> listarRespostasProjeto(@PageableDefault(sort = {"id"}) Pageable paginacao){
        var page = respostasProjetoService.listarRespostasProjeto(paginacao);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/aprovar/{id}")
    @Operation(summary = "Aprovação da Respostas Projetos", description = "Realiza a aprovação de uma resposta dentro de um projeto", tags = {"RespostaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<RespostasProjetoDetalhamentoDTO> aprovarRespostasProjeto(@PathVariable Long id){
        RespostasProjeto respostasProjeto = respostasProjetoService.alteraSituacapResposta(id, true);
        respostasProjetoService.atualizaSituacaoEtapa(respostasProjeto);
        return ResponseEntity.ok(new RespostasProjetoDetalhamentoDTO(respostasProjeto));
    }

    @PutMapping("/reprovar/{id}")
    @Operation(summary = "Reprovação da Respostas Projetos", description = "Realiza a reprovação de uma resposta dentro de um projeto", tags = {"RespostaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<RespostasProjetoDetalhamentoDTO> reprovarRespostasProjeto(@PathVariable Long id){
        return ResponseEntity.ok(new RespostasProjetoDetalhamentoDTO(respostasProjetoService.alteraSituacapResposta(id, false)));
    }

    @GetMapping("/listaretapasprojeto/{id}")
    @Operation(summary = "Listagem das respostas preenchidas e não preenchidas", description = "Realiza a listagem das perguntas/respostas respondidas e não respondidas", tags = {"RespostaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<RespostasPreenchidasENaoPreenchidasDTO>> listarRespostasPreenchidaseVazias(@PathVariable Long id){
        var page = respostasProjetoService.listarRespostasPreenchidaseVazias(id);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/contagemperguntas")
    @Operation(summary = "Contagem das perguntas por status", description = "Realiza uma contagem das perguntas por status para utilização no Dashboard", tags = {"RespostaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ContagemPerguntasDTO> contagemPerguntas(){
        return ResponseEntity.ok(respostasProjetoService.contarPerguntas());
    }


    @PutMapping("/alterarresposta")
    @Operation(summary = "Reprovação da Respostas Projetos", description = "Altera a resposta de um projeto", tags = {"RespostaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<RespostasProjetoDetalhamentoDTO> reprovarRespostasProjeto(@RequestBody AlterarRespostasProjetoDTO alterarRespostasProjetoDTO){
        return ResponseEntity.ok(new RespostasProjetoDetalhamentoDTO(respostasProjetoService.alteraResposta(alterarRespostasProjetoDTO)));
    }
}
