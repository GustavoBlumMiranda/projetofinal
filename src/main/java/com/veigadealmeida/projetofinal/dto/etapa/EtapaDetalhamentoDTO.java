package com.veigadealmeida.projetofinal.dto.etapa;

import com.veigadealmeida.projetofinal.domain.Etapa;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;

import java.util.List;
import java.util.stream.Collectors;

public record EtapaDetalhamentoDTO(
        Long id,
        String titulo,
        List<PerguntaDetalhamentoDTO> perguntas
) {
    public EtapaDetalhamentoDTO(Etapa etapa) {
        this(
                etapa.getId(),
                etapa.getTitulo(),
                etapa.getPerguntasEtapa().stream()
                        .map(pe -> new PerguntaDetalhamentoDTO(pe.getPergunta()))
                        .collect(Collectors.toList())
        );
    }
}