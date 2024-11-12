package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.StatusProjeto;

public record StatusProjetoDetalhamentoDTO(Long id, String status) {
    public StatusProjetoDetalhamentoDTO(StatusProjeto statusProjeto){
        this(statusProjeto.getId(), statusProjeto.getStatus());
    }
}
