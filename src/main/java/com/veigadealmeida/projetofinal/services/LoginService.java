package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.configuration.security.DTOTokenJWT;
import com.veigadealmeida.projetofinal.configuration.security.TokenJWTService;
import com.veigadealmeida.projetofinal.controller.customexceptions.BadRequestException;
import com.veigadealmeida.projetofinal.domain.Usuario;
import com.veigadealmeida.projetofinal.dto.usuario.DadosLoginUsuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager manager;

    private final TokenJWTService tokenJWTService;

    public LoginService(AuthenticationManager manager, TokenJWTService tokenJWTService) {
        this.manager = manager;
        this.tokenJWTService = tokenJWTService;
    }

    public DTOTokenJWT efetuaLogin(DadosLoginUsuario dadosLoginUsuario) {
        var tokenAutenticacao = new UsernamePasswordAuthenticationToken(dadosLoginUsuario.login(), dadosLoginUsuario.senha());

        try {
            var autenticacao = manager.authenticate(tokenAutenticacao);
            var tokenJWT = tokenJWTService.gerarToken((Usuario) autenticacao.getPrincipal());
            return new DTOTokenJWT(tokenJWT);
        } catch (BadRequestException ex) {
            throw new BadRequestException("Falha ao realizar o login. Usuário ou senha incorretos");
        }
    }
}
