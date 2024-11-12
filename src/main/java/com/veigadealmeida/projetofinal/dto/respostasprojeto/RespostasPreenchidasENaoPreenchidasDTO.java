package com.veigadealmeida.projetofinal.dto.respostasprojeto;

import com.veigadealmeida.projetofinal.domain.OpcaoResposta;
import com.veigadealmeida.projetofinal.domain.PerguntaEtapa;
import com.veigadealmeida.projetofinal.domain.RespostasProjeto;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;

import java.util.List;

public record RespostasPreenchidasENaoPreenchidasDTO(Long idPergunta,
                                                     String pergunta,
                                                     List<OpcaoRespostaDetalhamentoDTO> opcoesResposta,
                                                     String opcaoRespondida,
                                                     String respostaString,
                                                     Float respostaNumerica,
                                                     Boolean respondida,
                                                     Boolean aprovada) {

    public RespostasPreenchidasENaoPreenchidasDTO(PerguntaEtapa perguntaEtapa, RespostasProjeto respostasProjeto, List<OpcaoRespostaDetalhamentoDTO> listaOpcaoResposta){
        this(perguntaEtapa.getPergunta().getId(),
                perguntaEtapa.getPergunta().getDescricaoPergunta(),
                !listaOpcaoResposta.isEmpty() ? listaOpcaoResposta : null,
                respostasProjeto.getOpcaoResposta() != null ? respostasProjeto.getOpcaoResposta().getResposta() : null,
                respostasProjeto.getRespostaString() != null ? respostasProjeto.getRespostaString() : null,
                respostasProjeto.getRespostaNumerica() != null ? respostasProjeto.getRespostaNumerica() : null,
                respostasProjeto.getRespondida() != null ? respostasProjeto.getRespondida() : false,
                respostasProjeto.getRespondida() != null ? respostasProjeto.getAprovada() : null);
    }

}
