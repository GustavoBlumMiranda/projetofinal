package com.veigadealmeida.projetofinal.dto.perguntaetapa;

import com.veigadealmeida.projetofinal.domain.PerguntaEtapa;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaTemplateDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;

import java.util.Date;

public record PerguntaEtapaDetalhamentoDTO(Long id, PerguntaDetalhamentoDTO pergunta, EtapaTemplateDetalhamentoDTO etapa, Integer ordem, Boolean possuiControleFluxo , Date createdAt, Date updatedAt, Long version) {
    public PerguntaEtapaDetalhamentoDTO(PerguntaEtapa perguntaEtapa){
        this(perguntaEtapa.getId(), new PerguntaDetalhamentoDTO(perguntaEtapa.getPergunta()), new EtapaTemplateDetalhamentoDTO(perguntaEtapa.getEtapa()), perguntaEtapa.getOrdem(), perguntaEtapa.getPossuiControleFluxo(), perguntaEtapa.getCreatedAt(), perguntaEtapa.getUpdatedAt(), perguntaEtapa.getVersion());
    }
}
