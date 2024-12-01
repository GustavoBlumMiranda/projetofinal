package com.veigadealmeida.projetofinal.dto.pergunta;

import java.util.List;

public record PerguntaRespostaDetalhadaDTO(
        Long idPergunta,
        String descricaoPergunta,
        String resposta,
        Boolean respondida,
        String tipoPergunta,
        List<String> opcoesResposta
) {}