package com.veigadealmeida.projetofinal.dto.etapa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtivarOuDesativarEtapaDTO(@NotNull Long id, @NotBlank String justificativa) {
}
