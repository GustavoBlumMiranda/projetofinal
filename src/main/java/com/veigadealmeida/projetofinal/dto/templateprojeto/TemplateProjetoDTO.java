package com.veigadealmeida.projetofinal.dto.templateprojeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TemplateProjetoDTO(@NotNull Integer quantidadeEtapas, @NotBlank String titulo) {
}
