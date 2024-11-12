package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.dto.etapa.StatusEtapaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.statusetapaprojeto.AssociaTecnicoEtapaDTO;
import com.veigadealmeida.projetofinal.dto.statusetapaprojeto.ContagemEtapaDTO;
import com.veigadealmeida.projetofinal.dto.statusetapaprojeto.StatusEtapaProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.services.StatusEtapaProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/statusetapaprojeto")
public class StatusEtapaProjetoController {
    private final StatusEtapaProjetoService statusEtapaProjetoService;

    public StatusEtapaProjetoController(StatusEtapaProjetoService statusEtapaProjetoService){
        this.statusEtapaProjetoService = statusEtapaProjetoService;
    }
    // DESATIVADO PARA TESTES - DELETAR EM CASO DE NÃO UTILIZAÇÃO
    /*@PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de StatusEtapaProjeto", description = "Realiza o cadastro de um Status Etapa em um Projeto (Tabela status_etapa_projeto)", tags = {"StatusEtapaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<StatusEtapaProjetoDetalhamentoDTO> cadastrarStatusEtapaProjeto(@RequestBody @Valid StatusEtapaProjetoDTO statusEtapaProjetoDTO, UriComponentsBuilder uriComponentsBuilder){
        StatusEtapaProjetoDetalhamentoDTO statusEtapaProjetoDetalhamentoDTO = statusEtapaProjetoService.cadastrarStatusEtapaProjeto(statusEtapaProjetoDTO);
        var uri = uriComponentsBuilder.path("/statusetapaprojeto/cadastrar/{id}").buildAndExpand(statusEtapaProjetoDetalhamentoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(statusEtapaProjetoDetalhamentoDTO);
    }*/

    @GetMapping("/listar")
    @Operation(summary = "Listagem dos StatusEtapaProjetos", description = "Realiza a listagem paginada dos status etapa projetos existentes", tags = {"StatusEtapaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<StatusEtapaProjetoDetalhamentoDTO>> listarStatusEtapaProjeto(@PageableDefault(sort = {"id"}) Pageable paginacao){
        var page = statusEtapaProjetoService.listarStatusEtapaProjeto(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/listastatus")
    @Operation(summary = "Listagem dos StatusEtapa", description = "Realiza a listagem paginada dos status etapa existentes (tabela status_etapa)", tags = {"StatusEtapaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Page<StatusEtapaDetalhamentoDTO>> listarStatusEtapa(@PageableDefault(sort = {"id"}) Pageable paginacao){
        var page = statusEtapaProjetoService.listarStatusEtapa(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/listarporprojeto/{id}")
    @Operation(summary = "Listagem por projeto", description = "Realiza a listagem dos status etapa projetos por projeto definido", tags = {"StatusEtapaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<StatusEtapaProjetoDetalhamentoDTO>> listarStatusEtapaProjetoPorProjeto(@PathVariable Long id){
        List<StatusEtapaProjetoDetalhamentoDTO> statusEtapaProjetoDetalhamentoDTOList = statusEtapaProjetoService.listarStatusEtapaProjetoPorProjeto(id);
        return ResponseEntity.ok(statusEtapaProjetoDetalhamentoDTOList);
    }

    @GetMapping("/contagem")
    @Operation(summary = "Contagem dos StatusEtapaProjeto", description = "Realiza a contagem dos StatusEtapaProjeto existentes", tags = {"StatusEtapaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ContagemEtapaDTO> contagemStatusEtapaProjeto(){
        ContagemEtapaDTO contagemEtapaDTO = statusEtapaProjetoService.contarEtapas();
        return ResponseEntity.ok(contagemEtapaDTO);
    }

    @PutMapping("/associatecnico")
    @Operation(summary = "Associa tecnico", description = "Realiza a associação de um tecnico a uma Etapa", tags = {"StatusEtapaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<StatusEtapaProjetoDetalhamentoDTO> associarTecnicoEtapa(@RequestBody @Valid AssociaTecnicoEtapaDTO associaTecnicoEtapaDTO){
        return ResponseEntity.ok(statusEtapaProjetoService.associaTecnico(associaTecnicoEtapaDTO));
    }

    @PutMapping("/iniciar/{id}")
    @Operation(summary = "Associa tecnico", description = "Inicia uma etapa", tags = {"StatusEtapaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<StatusEtapaProjetoDetalhamentoDTO> associarTecnicoEtapa(@PathVariable Long id){
        return ResponseEntity.ok(statusEtapaProjetoService.iniciarEtapa(id));
    }

    @GetMapping("/listarportecnicologado")
    @Operation(summary = "Lista por tecnico logado", description = "Lista por tecnico logado", tags = {"StatusEtapaProjetoController"}, security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<StatusEtapaProjetoDetalhamentoDTO>> listarPorTecnicoLogado(){
        return ResponseEntity.ok(statusEtapaProjetoService.listarPorTecnico());
    }

}
