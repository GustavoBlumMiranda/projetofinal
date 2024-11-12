package com.veigadealmeida.projetofinal.dto.etapa;

import com.veigadealmeida.projetofinal.domain.Etapa;

public record EtapaTemplateDetalhamentoDTO(Long id, String titulo, Boolean ativo, String justificativa){
    public EtapaTemplateDetalhamentoDTO(Etapa etapa){
        this(etapa.getId(), etapa.getTitulo(), etapa.getAtivo(), etapa.getJustificativa());
    }
}
