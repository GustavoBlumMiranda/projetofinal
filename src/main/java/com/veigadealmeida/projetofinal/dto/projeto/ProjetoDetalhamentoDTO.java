package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.Projeto;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaDetalhamentoDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record ProjetoDetalhamentoDTO(
        Long id,
        String titulo,
        Boolean possuiUsuarios,
        Date createdAt,
        Date updatedAt,
        List<EtapaDetalhamentoDTO> etapas

) {
    public ProjetoDetalhamentoDTO(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getTitulo(),
                !projeto.getUsuarios().isEmpty(),
                projeto.getCreatedAt(),
                projeto.getUpdatedAt(),
                projeto.getEtapasProjeto().stream()
                        .map(ep -> new EtapaDetalhamentoDTO(ep.getEtapa()))
                        .collect(Collectors.toList())
        );
    }
}
