package com.veigadealmeida.projetofinal.dto.cargo;

import com.veigadealmeida.projetofinal.domain.Cargo;

public record CargoDetalhamentoDTO(Long id, String descricaoCargo) {
    public CargoDetalhamentoDTO(Cargo cargo){
        this(cargo.getId(), cargo.getDescricaoCargo());
    }
}
