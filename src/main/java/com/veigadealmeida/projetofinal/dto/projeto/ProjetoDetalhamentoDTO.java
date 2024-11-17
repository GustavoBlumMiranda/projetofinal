package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.Projeto;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.templateprojeto.TemplateProjetoDetalhamentoDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record ProjetoDetalhamentoDTO(
        Long id,
        String titulo,
        Date createdAt,
        Date updatedAt,
        List<EtapaDetalhamentoDTO> etapas
) {
    public ProjetoDetalhamentoDTO(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getCreatedAt(),
                projeto.getUpdatedAt(),
                projeto.getEtapasProjeto().stream()
                        .map(ep -> new EtapaDetalhamentoDTO(ep.getEtapa()))
                        .collect(Collectors.toList())
        );
    }
}
