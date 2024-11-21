package com.veigadealmeida.projetofinal.dto.respostasprojeto;


import com.veigadealmeida.projetofinal.domain.RespostasEtapaEmUso;
import com.veigadealmeida.projetofinal.enumerators.TipoPerguntaEnum;

import java.util.Date;

public record RespostaPerguntaDetalhamentoDTO(Long idResposta, Long idEtapaEmUso, Long idUsuario, String codusuario, Date dataResposta, String Pergunta, String tipoResposta, String resposta) {

    public RespostaPerguntaDetalhamentoDTO(RespostasEtapaEmUso respostasEtapaEmUso) {
        this(
                respostasEtapaEmUso.getId(),
                respostasEtapaEmUso.getEtapaEmUso().getId(),
                respostasEtapaEmUso.getEtapaEmUso().getUsuario().getId(),
                respostasEtapaEmUso.getEtapaEmUso().getUsuario().getCodUsuario(),
                respostasEtapaEmUso.getCreatedAt(),
                respostasEtapaEmUso.getPergunta().getDescricaoPergunta(),
                respostasEtapaEmUso.getPergunta().getTipoPergunta().getDescricao(),
                determineResposta(respostasEtapaEmUso)
        );
    }

    private static String determineResposta(RespostasEtapaEmUso respostasEtapaEmUso) {
        if (respostasEtapaEmUso.getPergunta().getTipoPergunta() == TipoPerguntaEnum.NUMERICO) {
            return respostasEtapaEmUso.getRespostaNumerica().toString();
        } else if (respostasEtapaEmUso.getPergunta().getTipoPergunta() == TipoPerguntaEnum.MULTIPLA_ESCOLHA) {
            return respostasEtapaEmUso.getOpcaoResposta().getResposta();
        } else {
            return respostasEtapaEmUso.getRespostaString();
        }
    }
}



