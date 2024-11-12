package com.veigadealmeida.projetofinal.dto.etapatemplateprojeto;

import com.veigadealmeida.projetofinal.domain.EtapaTemplateProjeto;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaTemplateDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.templateprojeto.TemplateProjetoDetalhamentoDTO;

import java.util.Date;

public record EtapaTemplateProjetoDetalhamentoDTO(Long id, EtapaTemplateDetalhamentoDTO etapa, TemplateProjetoDetalhamentoDTO templateProjeto, Integer ordemEtapa, Date createdAt, Date updatedAt, Long version) {
    public EtapaTemplateProjetoDetalhamentoDTO(EtapaTemplateProjeto etapaTemplateProjeto){
        this(etapaTemplateProjeto.getId(), new EtapaTemplateDetalhamentoDTO(etapaTemplateProjeto.getEtapa()), new TemplateProjetoDetalhamentoDTO(etapaTemplateProjeto.getTemplateProjeto()) , etapaTemplateProjeto.getOrdemEtapa(), etapaTemplateProjeto.getCreatedAt(), etapaTemplateProjeto.getUpdatedAt(), etapaTemplateProjeto.getVersion());
    }
}
