package com.veigadealmeida.projetofinal.controller;

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
    public ResponseEntity<ProjetoDetalhamentoDTO> cadastrarProjeto(@RequestBody @Valid ProjetoCadastroDTO projetoCadastroDTO, UriComponentsBuilder uriComponentsBuilder){
        ProjetoDetalhamentoDTO projetoDetalhamentoDTO = projetoService.cadastrarProjeto(projetoCadastroDTO);
        var uri = uriComponentsBuilder.path("/projeto/cadastrar/{id}").buildAndExpand(projetoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(projetoDetalhamentoDTO);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listagem dos Projetos", description = "Realiza a listagem paginada dos projetos existentes", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<ProjetoDetalhamentoDTO>> listarProjetosSemAssociacao(@PageableDefault(sort = {"id"}) Pageable paginacao){
        var page = projetoService.listarProjetos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/listarsemusuario")
    @Operation(summary = "Listagem dos Projetos", description = "Realiza a listagem paginada dos projetos existentes", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<ProjetoDetalhamentoDTO>> listarProjetos(@PageableDefault(sort = {"id"}) Pageable paginacao){
        var page = projetoService.listarProjetosSemAssociacao(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar um Projetos", description = "Realiza a busca de um Projetos por ID", tags = {"ProjetoController"})
    public ResponseEntity<ProjetoDetalhamentoDTO> buscaProjetoPorId(@PathVariable(value = "id") long id){
        ProjetoDetalhamentoDTO projetoDetalhamentoDTO = projetoService.buscar(id);
        return ResponseEntity.ok(projetoDetalhamentoDTO);
    }

    @PostMapping("/associarusuario")
    @Operation(summary = "Associar Usuário ao Projeto", description = "Associa um usuário existente a um projeto existente", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<String> associarUsuarioAoProjeto(@RequestParam Long projetoId, @RequestParam Long usuarioId) {
        return projetoService.associarUsuarioAoProjeto(projetoId, usuarioId);
    }

    @GetMapping("/acompanharporusuario/{usuarioId}")
    @Operation(summary = "Listar Projetos por Usuário", description = "Retorna a listagem de acompanhamento de projetos associados a um usuário específico", tags = {"ProjetoController"})
    public ResponseEntity<Page<ProjetoSimplesDetalhamentoDTO>> acompanharProjetosPorUsuario(@PathVariable(value = "usuarioId") Long usuarioId, Pageable paginacao) {
        Page<ProjetoSimplesDetalhamentoDTO> projetos = projetoService.listarProjetoPorUsuario(paginacao, usuarioId);
        return ResponseEntity.ok(projetos);
    }

    @GetMapping("/acompanhar")
    @Operation(summary = "Listar Projetos por Usuário", description = "Retorna a listagem de acompanhamento de projetos", tags = {"ProjetoController"})
    public ResponseEntity<Page<ProjetoSimplesDetalhamentoDTO>> acompanharProjetos(Pageable paginacao) {
        Page<ProjetoSimplesDetalhamentoDTO> projetos = projetoService.acompanharProjetos(paginacao);
        return ResponseEntity.ok(projetos);
    }

    @GetMapping("/acompanhar/ususariologado")
    @Operation(summary = "Listar Projetos por Usuário", description = "Retorna a listagem de acompanhamento de projetos do usuario logado", tags = {"ProjetoController"})
    public ResponseEntity<Page<ProjetoSimplesDetalhamentoDTO>> acompanharProjetosUsuarioLogado(Pageable paginacao) {
        Page<ProjetoSimplesDetalhamentoDTO> projetos = projetoService.listarProjetoPorUsuario(paginacao, null);
        return ResponseEntity.ok(projetos);
    }

    @PutMapping("/editar")
    @Operation(summary = "Altera dados de um projeto", description = "Altera dados de um projeto", tags = {"ProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ProjetoDetalhamentoDTO> editarProjeto(@RequestBody AlteraProjetoDTO alterarProjetoDTO){
        ProjetoDetalhamentoDTO projetoDetalhamentoDTO = projetoService.editarProjeto(alterarProjetoDTO);
        return ResponseEntity.ok(projetoDetalhamentoDTO);
    }


}
