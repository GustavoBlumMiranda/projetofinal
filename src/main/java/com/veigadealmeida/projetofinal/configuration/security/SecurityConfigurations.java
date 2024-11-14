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
                    .requestMatchers(HttpMethod.POST, "/resetsenha/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/resetsenha/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/usuario/buscarusuariologado").hasAnyAuthority("dev", "gestor", "cordenador", "administrador", "tecnico")
                    .requestMatchers("/usuario/**").hasAnyAuthority("dev", "gestor", "administrador")
                    .requestMatchers(HttpMethod.POST, "/cargo/cadastrar").hasAnyAuthority("dev", "administrador", "gestor")
                    .requestMatchers("/etapa/**").hasAnyAuthority("dev", "gestor", "cordenador")
                    .requestMatchers("/etapatemplateprojeto/**").hasAnyAuthority("dev", "gestor", "cordenador")
                    .requestMatchers(HttpMethod.POST,"/opcaoresposta/cadastrar").hasAnyAuthority("dev", "gestor", "cordenador", "administrador", "tecnico")
                    .requestMatchers("/pergunta/**").hasAnyAuthority("dev", "gestor", "cordenador", "tecnico")
                    .requestMatchers(HttpMethod.POST,"/perguntaetapa/associar").hasAnyAuthority("dev", "gestor", "cordenador")
                    .requestMatchers("/projeto/**").hasAnyAuthority("dev", "gestor", "cordenador")
                    .requestMatchers("/respostasprojeto/**").hasAnyAuthority("dev", "gestor", "cordenador")
                    .requestMatchers(HttpMethod.GET,"/statusetapaprojeto/listarportecnicologado").hasAnyAuthority("dev", "tecnico")
                    .requestMatchers("/statusetapaprojeto/**").hasAnyAuthority("dev", "gestor", "cordenador", "tecnico")
                    .requestMatchers("/templateprojeto/**").hasAnyAuthority("dev", "gestor", "cordenador")
                    .requestMatchers(HttpMethod.GET, "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/","/v3/api-docs/**").permitAll() // Permite acesso ao Springdoc OpenAPI
                    .requestMatchers(HttpMethod.GET, "/actuator/", "/actuator/**").permitAll() // Permite acesso ao actuator
                    .anyRequest().authenticated()
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

