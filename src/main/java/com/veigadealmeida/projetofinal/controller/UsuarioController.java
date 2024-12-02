package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.domain.Usuario;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioEditarDTO;
import com.veigadealmeida.projetofinal.repository.UsuarioRepository;
import com.veigadealmeida.projetofinal.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RestController
@CrossOrigin
@RequestMapping("/usuario")
public class UsuarioController implements UserDetailsService {
	private final UsuarioRepository usuarioRepository;

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService){
		this.usuarioRepository = usuarioRepository;
		this.usuarioService = usuarioService;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository.findByLogin(username);
	}


	@PostMapping("/cadastrar")
	@Operation(summary = "Realiza o cadastro de usuarios", description = "Só pode ser realizado por um usuario logado", tags = {"UsuarioController"})
	public ResponseEntity<UsuarioDetalhamentoDTO> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO, UriComponentsBuilder uriComponentsBuilder){
		UsuarioDetalhamentoDTO usuarioDetalhamentoDTO = usuarioService.cadastrarUsuario(usuarioDTO);
		var uri = uriComponentsBuilder.path("/usuario/cadastrar/{id}").buildAndExpand(usuarioDetalhamentoDTO.id()).toUri();
		return ResponseEntity.created(uri).body(usuarioDetalhamentoDTO);
	}

	@RequestMapping(value = "/deletar/{id}", method = RequestMethod.DELETE)
	@Operation(summary = "Deletar", description = "Deleta um usuário", tags = {"UsuarioController"})
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
	{
		usuarioService.Delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscar/{id}")
	@Operation(summary = "Buscar um usuário", description = "Realiza a busca de um usuário por ID", tags = {"UsuarioController"})
	public ResponseEntity<UsuarioDetalhamentoDTO> buscaUsuarioPorId(@PathVariable(value = "id") long id){
		UsuarioDetalhamentoDTO usuarioDetalhamentoDTO = usuarioService.getUsuarioPorId(id);
		return ResponseEntity.ok(usuarioDetalhamentoDTO);
	}

	@GetMapping("/buscarusuariologado")
	@Operation(summary = "Buscar o usuário logado", description = "Realiza a busca do usuário logado", tags = {"UsuarioController"})
	public ResponseEntity<UsuarioDetalhamentoDTO> buscarUsuarioLogado(){
		UsuarioDetalhamentoDTO usuarioDetalhamentoDTO = usuarioService.getUsuarioPorId(null);
		return ResponseEntity.ok(usuarioDetalhamentoDTO);
	}

	@GetMapping("/listar")
	@Operation(summary = "Listagem de Usuários", description = "Realiza a listagem paginada dos usuários", tags = {"UsuarioController"})
	public ResponseEntity<Page<UsuarioDetalhamentoDTO>> listarUsuario(@PageableDefault(sort = {"id"}, size = 200) Pageable paginacao){
		var page = usuarioService.listarUsuario(paginacao);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/listarporprojeto/{projetoId}")
	@Operation(summary = "Listagem de Usuários Associação", description = "Realiza a listagem dos usuários que estão associados ao projeto especificado.", tags = {"UsuarioController"})
	public ResponseEntity<List<UsuarioDetalhamentoDTO>> listarUsuariosPorProjeto(@PathVariable Long projetoId) {
		var retorno = usuarioService.listarPorProjeto(projetoId);
		return ResponseEntity.ok(retorno);
	}

	@GetMapping("/listardisponiveis/{projetoId}")
	@Operation(summary = "Listagem de Usuários Disponíveis para Associação", description = "Realiza a listagem paginada dos usuários que não estão associados ao projeto especificado.", tags = {"UsuarioController"})
	public ResponseEntity<Page<UsuarioDetalhamentoDTO>> listarUsuariosDisponiveisParaAssociacao(@PageableDefault(sort = {"id"}, size = 200) Pageable paginacao, @PathVariable Long projetoId) {
		var page = usuarioService.listarUsuariosDisponiveisParaAssociacao(paginacao, projetoId);
		Page<UsuarioDetalhamentoDTO> usuariosDetalhados = page.map(UsuarioDetalhamentoDTO::new);
		return ResponseEntity.ok(usuariosDetalhados);
	}


	@PutMapping("/editar")
	@Operation(summary = "Edita usuário", description = "Realiza a edição de um usuário", tags = {"UsuarioController"})
	public ResponseEntity<UsuarioDetalhamentoDTO> editaUsuario(@RequestBody @Valid UsuarioEditarDTO usuarioEditarDTO){
		return ResponseEntity.ok(usuarioService.editarUsuario(usuarioEditarDTO));

	}

}
