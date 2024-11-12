package com.veigadealmeida.projetofinal.dto.statusetapaprojeto;

import java.util.Date;

public record StatusEtapaProjetoDTO(Long usuarioId, Long statusEtapaId, Long projetoId, Long etapaId, Date dataInicio, Date dataFim) {
}