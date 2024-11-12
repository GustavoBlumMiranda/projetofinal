package com.veigadealmeida.projetofinal.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record ResetSenhaDTO(@NotBlank String email) {
}
