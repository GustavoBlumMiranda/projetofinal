
package com.veigadealmeida.projetofinal.configuration.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.veigadealmeida.projetofinal.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenJWTService {
    @Value("${projetofinal.security.token.secret}")
    private String segredo;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(segredo); // PERGUNTAR ALGORITMO UTILIZADO
            return JWT.create()
                    .withIssuer("PROJETOFINAL") // SUBSTITUIR PELO NOME DA EMPRESA
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritmo = Algorithm.HMAC256(segredo); // PERGUNTAR ALGORITMO UTILIZADO
            return JWT.require(algoritmo)
                    .withIssuer("PROJETOFINAL") // SUBSTITUIR PELO NOME DA EMPRESA
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("TokenJWT inválido ou expirado");
        }
    }

    public static String getBearerTokenHeader() {
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (authHeader != null){
            return authHeader.replace("Bearer","").strip();
        }
        return authHeader;
    }

    private Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }

}


