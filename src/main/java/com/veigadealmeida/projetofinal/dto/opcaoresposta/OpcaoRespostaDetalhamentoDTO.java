package com.veigadealmeida.projetofinal.dto.opcaoresposta;

import com.veigadealmeida.projetofinal.domain.OpcaoResposta;


public record OpcaoRespostaDetalhamentoDTO(Long id, String resposta, Long proximaPerguntaId, Long perguntaId) {
    public OpcaoRespostaDetalhamentoDTO(OpcaoResposta opcaoResposta){
        this(opcaoResposta.getId(), opcaoResposta.getResposta(), opcaoResposta.getProximaPergunta() != null ? opcaoResposta.getProximaPergunta().getId() : null, opcaoResposta.getPergunta().getId());
    }
}
