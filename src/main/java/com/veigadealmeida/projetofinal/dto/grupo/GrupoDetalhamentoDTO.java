package com.veigadealmeida.projetofinal.dto.grupo;

import com.veigadealmeida.projetofinal.domain.Grupo;

public record GrupoDetalhamentoDTO(Long id, String descricaoCargo) {
    public GrupoDetalhamentoDTO(Grupo grupo){
        this(grupo.getId(), grupo.getDescricaoGrupo());
    }
}
