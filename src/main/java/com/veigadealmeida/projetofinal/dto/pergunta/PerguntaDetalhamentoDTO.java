package com.veigadealmeida.projetofinal.dto.pergunta;

import com.veigadealmeida.projetofinal.domain.Pergunta;

public record PerguntaDetalhamentoDTO(Long id, String descricaoPergunta, String tipoResposta) {
    public PerguntaDetalhamentoDTO(Pergunta pergunta){
        this(pergunta.getId(), pergunta.getDescricaoPergunta(), pergunta.getTipoPergunta().getDescricao());
    }


}
