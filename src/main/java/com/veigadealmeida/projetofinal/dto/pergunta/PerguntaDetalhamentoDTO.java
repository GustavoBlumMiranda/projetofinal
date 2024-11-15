package com.veigadealmeida.projetofinal.dto.pergunta;

import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;

import java.util.List;
import java.util.stream.Collectors;

public record PerguntaDetalhamentoDTO(Long id, String descricaoPergunta, String tipoPergunta, List<OpcaoRespostaDetalhamentoDTO> opcoesResposta) {
    public PerguntaDetalhamentoDTO(Pergunta pergunta){
        this(pergunta.getId(),
                pergunta.getDescricaoPergunta(),
                pergunta.getTipoPergunta().getDescricao(),
                pergunta.getTipoPergunta().name().equals("MULTIPLA_ESCOLHA")
                        ? pergunta.getOpcoesResposta().stream()
                        .map(OpcaoRespostaDetalhamentoDTO::new)
                        .collect(Collectors.toList())
                        : null
        );
    }


}
