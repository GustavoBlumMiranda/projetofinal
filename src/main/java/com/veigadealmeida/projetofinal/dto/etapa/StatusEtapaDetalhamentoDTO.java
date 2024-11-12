package com.veigadealmeida.projetofinal.dto.etapa;

import com.veigadealmeida.projetofinal.domain.StatusEtapa;

public record StatusEtapaDetalhamentoDTO(Long id, String status) {
    public StatusEtapaDetalhamentoDTO(StatusEtapa statusEtapa) {
        this(statusEtapa.getId(), statusEtapa.getStatus());
    }
}
