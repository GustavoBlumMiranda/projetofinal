
package com.veigadealmeida.projetofinal.configuration.security;


import com.veigadealmeida.projetofinal.domain.AlterarSenhaToken;
import com.veigadealmeida.projetofinal.repository.TokenResetSenhaRepository;
import com.veigadealmeida.projetofinal.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component//Filtro para interceptar as requisições e obter o token do header
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenJWTService tokenJWTService;
    private final UsuarioRepository usuarioRepository;
    private final TokenResetSenhaRepository tokenResetSenhaRepository;

    public SecurityFilter(TokenJWTService tokenJWTService, UsuarioRepository usuarioRepository, TokenResetSenhaRepository tokenResetSenhaRepository) {
        this.tokenJWTService = tokenJWTService;
        this.usuarioRepository = usuarioRepository;
        this.tokenResetSenhaRepository = tokenResetSenhaRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if (tokenJWT != null) {
            var subject = tokenJWTService.getSubject(tokenJWT);
            var usuario = usuarioRepository.findByLogin(subject);

            var auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null){
            return authHeader.replace("Bearer","").strip();
        }
        return null;
    }

    public Boolean validaTrocaDeSenha(String token) {
        final AlterarSenhaToken tokenSenha = tokenResetSenhaRepository.findByToken(token);
        if(tokenSenha == null || tokenSenha.tokenExpirado(tokenSenha)){
            return false;
        }
        return true;
    }

}

