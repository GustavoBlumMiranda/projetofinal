package com.veigadealmeida.projetofinal.dto.respostasprojeto;


import com.veigadealmeida.projetofinal.domain.PerguntaEtapa;

public record RespostasPreenchidasENaoPreenchidasDTO(Long idPergunta,
                                                     String pergunta) {

    public RespostasPreenchidasENaoPreenchidasDTO(PerguntaEtapa perguntaEtapa){
        this(perguntaEtapa.getPergunta().getId(),
                perguntaEtapa.getPergunta().getDescricaoPergunta());
    }

}
