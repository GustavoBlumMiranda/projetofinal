package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.domain.Projeto;
import com.veigadealmeida.projetofinal.dto.projeto.*;
import com.veigadealmeida.projetofinal.services.ProjetoService;
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
@RequestMapping("/projeto")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService){
        this.projetoService = projetoService;
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de Projeto", description = "Realiza o cadastro de um Projeto (Titulo, Template, DataInicio, DataFim)", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ProjetoDetalhamentoDTO> cadastrarProjeto(@RequestBody @Valid ProjetoDTO projetoDTO, UriComponentsBuilder uriComponentsBuilder){
        ProjetoDetalhamentoDTO projetoDetalhamentoDTO = projetoService.cadastrarProjeto(projetoDTO);
        var uri = uriComponentsBuilder.path("/projeto/cadastrar/{id}").buildAndExpand(projetoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(projetoDetalhamentoDTO);
    }

    @PutMapping("/atualizarstatusprojeto")
    @Operation(summary = "Alteração de Estado do Projeto", description = "Atualiza o status de um Projeto", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Projeto> atualizarStatusProjeto(@RequestBody @Valid AtualizarStatusProjetoDTO atualizarStatusProjetoDTO){
        return ResponseEntity.ok(projetoService.atualizarStatusProjeto(atualizarStatusProjetoDTO));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listagem dos Projetos", description = "Realiza a listagem paginada dos projetos existentes", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<ProjetoDetalhamentoDTO>> listarProjetos(@PageableDefault(sort = {"id"}) Pageable paginacao){
        paginacao = Pageable.unpaged();
        var page = projetoService.listarProjetos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/listarstatus")
    @Operation(summary = "Listagem dos Status Projetos", description = "Realiza a listagem dos status projetos existentes (tabela status_projeto)", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<StatusProjetoDetalhamentoDTO>> listarStatusProjeto(){
        List<StatusProjetoDetalhamentoDTO> listaStatus = projetoService.listarStatusProjeto();
        return ResponseEntity.ok(listaStatus);
    }

    @GetMapping("/contagem")
    @Operation(summary = "Contagem dos Projetos", description = "Realiza a contagem dos projetos existentes", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ContagemProjetoDTO> contagemProjeto(){
        ContagemProjetoDTO contagemProjetoDTO = projetoService.contarProjetos();
        return ResponseEntity.ok(contagemProjetoDTO);
    }

    @PutMapping("/alterar")
    @Operation(summary = "Altera dados de um projeto", description = "Altera dados de um projeto", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ProjetoDetalhamentoDTO> alteraProjeto(@RequestBody AlteraProjetoDTO alterarProjetoDTO){
        ProjetoDetalhamentoDTO projetoDetalhamentoDTO = projetoService.alteraProjeto(alterarProjetoDTO);
        return ResponseEntity.ok(projetoDetalhamentoDTO);
    }

    @PutMapping("/associarcoordenador")
    @Operation(summary = "Associa coordenador a um projeto", description = "Realiza a associação de um coordenador a um projeto", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<AssociaCoordenadorComProjetoDTO> associarCoordenadorComProjeto(@RequestBody @Valid AssociaCoordenadorComProjetoDTO associaCoordenadorComProjetoDTO){
        return ResponseEntity.ok(projetoService.associaCoordenadorComProjeto(associaCoordenadorComProjetoDTO));
    }

}
