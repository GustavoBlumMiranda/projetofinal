package com.veigadealmeida.projetofinal.controller;

import com.veigadealmeida.projetofinal.configuration.security.DTOTokenJWT;
import com.veigadealmeida.projetofinal.dto.usuario.DadosLoginUsuario;
import com.veigadealmeida.projetofinal.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@RestController
@CrossOrigin
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping
	@Operation(summary = "Login do usuario", description = "Recebe Login e Senha, retorna Token de autenticação (Bearer)", tags = {"LoginController"})
	public ResponseEntity<DTOTokenJWT> efetuaLogin(@RequestBody @Valid DadosLoginUsuario dadosLoginUsuario){
		DTOTokenJWT dtoTokenJWT = loginService.efetuaLogin(dadosLoginUsuario);
		return ResponseEntity.ok(dtoTokenJWT);
	}

}
