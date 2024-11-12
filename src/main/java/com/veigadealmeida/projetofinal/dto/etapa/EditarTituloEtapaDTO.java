package com.veigadealmeida.projetofinal.dto.etapa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarTituloEtapaDTO(@NotNull Long id, @NotBlank String titulo) {
}
