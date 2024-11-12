package com.veigadealmeida.projetofinal.dto.pergunta;

import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.RespostasProjeto;

public record PerguntaDetalhamentoDTO(Long id, String descricaoPergunta, String tipoResposta, Long idProximaPergunta, Boolean ativo) {
    public PerguntaDetalhamentoDTO(Pergunta pergunta){
        this(pergunta.getId(), pergunta.getDescricaoPergunta(), pergunta.getTipoResposta().getTipo(), pergunta.getIdProximaPergunta(), pergunta.getAtivo());
    }


}
