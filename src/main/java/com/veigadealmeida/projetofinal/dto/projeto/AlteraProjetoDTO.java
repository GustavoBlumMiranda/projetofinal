package com.veigadealmeida.projetofinal.dto.projeto;

import java.util.Date;

public record AlteraProjetoDTO(Long id, String titulo, Date dataInicio, Date dataFim, Date dataPrevistaInicio, Date dataPrevistaFim, Long statusProjetoId, Long idTemplateProjeto) {
}
