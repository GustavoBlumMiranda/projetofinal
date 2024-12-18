package com.veigadealmeida.projetofinal.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

        private final SecurityFilter securityFilter;

        public SecurityConfigurations(SecurityFilter securityFilter) {
            this.securityFilter = securityFilter;
        }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/login").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/projeto/cadastrar").hasAnyAuthority("Administrador")
            .requestMatchers(HttpMethod.PUT, "/projeto/editar/**").hasAnyAuthority("Administrador")
            .requestMatchers(HttpMethod.DELETE, "/projeto/excluir/**").hasAnyAuthority("Administrador")
            .requestMatchers(HttpMethod.POST, "/pergunta/responder").hasAuthority("Colaborador")
            .requestMatchers(HttpMethod.PUT, "/etapa/editar/**").hasAuthority("Administrador")
            .requestMatchers(HttpMethod.POST, "/projeto/associarusuario").hasAnyAuthority("Administrador")
            .requestMatchers(HttpMethod.DELETE, "/projeto/desassociar/**").hasAnyAuthority("Administrador")
            .requestMatchers(HttpMethod.GET, "/projeto/acompanhar").hasAnyAuthority("Administrador")
            .requestMatchers(HttpMethod.GET, "/projeto/acompanhar/ususariologado").hasAuthority("Colaborador")
            .anyRequest().authenticated() // Requer autenticação para todas as outras requisições
            .and().cors()
            .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }



    @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

