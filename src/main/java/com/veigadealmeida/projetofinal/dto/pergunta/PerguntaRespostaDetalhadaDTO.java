package com.veigadealmeida.projetofinal.dto.pergunta;

import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;

import java.util.List;

public record PerguntaRespostaDetalhadaDTO(
        Long idPergunta,
        String descricaoPergunta,
        String resposta,
        Boolean respondida,
        String tipoPergunta,
        List<OpcaoRespostaDetalhamentoDTO> opcoesResposta
) {}