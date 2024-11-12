package com.veigadealmeida.projetofinal.dto.pergunta;

import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.RespostasProjeto;
import com.veigadealmeida.projetofinal.dto.respostasprojeto.RespostasProjetoDetalhamentoDTO;

public record PerguntaEmUsoDetalhamentoDTO(Long id, String descricaoPergunta, String tipoResposta, Long idProximaPergunta, Boolean ativo, String status,
                                           RespostasProjetoDetalhamentoDTO respostasProjetoDetalhamentoDTO) {
    public PerguntaEmUsoDetalhamentoDTO(Pergunta pergunta, RespostasProjeto respostasProjeto){
        this(pergunta.getId(), pergunta.getDescricaoPergunta(), pergunta.getTipoResposta().getTipo(), pergunta.getIdProximaPergunta(), pergunta.getAtivo(),
                determineStatus(respostasProjeto), respostasProjeto != null ? new RespostasProjetoDetalhamentoDTO(respostasProjeto) : null );
    }

    private static String determineStatus(RespostasProjeto respostasProjeto) {
        if (respostasProjeto == null || !respostasProjeto.getRespondida()) {
            return "NAO RESPONDIDO";
        } else if (respostasProjeto.getAprovada() == null) {
            return "AGUARDANDO APROVACAO";
        } else if (respostasProjeto.getAprovada()) {
            return "APROVADO";
        } else {
            return "REPROVADO";
        }
    }
}
