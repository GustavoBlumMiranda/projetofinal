package com.veigadealmeida.projetofinal.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosLoginUsuario(@NotBlank String login, @NotBlank String senha) {
}
