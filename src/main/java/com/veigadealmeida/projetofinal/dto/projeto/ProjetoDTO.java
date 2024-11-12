package com.veigadealmeida.projetofinal.dto.projeto;


import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ProjetoDTO(@NotNull String titulo, Date dataInicio, Date dataFim, Date dataPrevistaInicio, Date dataPrevistaFim,@NotNull Long statusProjetoId, Long idTemplateProjeto){
}
