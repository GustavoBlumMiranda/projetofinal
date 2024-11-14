package com.veigadealmeida.projetofinal.dto.opcaoresposta;

import com.veigadealmeida.projetofinal.domain.OpcaoResposta;


public record OpcaoRespostaDetalhamentoDTO(Long id, String resposta, Long perguntaId) {
    public OpcaoRespostaDetalhamentoDTO(OpcaoResposta opcaoResposta){
        this(opcaoResposta.getId(), opcaoResposta.getResposta(), opcaoResposta.getPergunta().getId());
    }
}
