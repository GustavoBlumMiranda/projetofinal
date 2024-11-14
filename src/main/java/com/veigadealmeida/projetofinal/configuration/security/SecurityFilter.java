
package com.veigadealmeida.projetofinal.configuration.security;


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

    public SecurityFilter(TokenJWTService tokenJWTService, UsuarioRepository usuarioRepository) {
        this.tokenJWTService = tokenJWTService;
        this.usuarioRepository = usuarioRepository;
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


}

