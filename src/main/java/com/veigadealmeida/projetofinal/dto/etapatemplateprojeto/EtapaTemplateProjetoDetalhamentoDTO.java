package com.veigadealmeida.projetofinal.dto.etapatemplateprojeto;

import com.veigadealmeida.projetofinal.domain.EtapaProjeto;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaTemplateDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.templateprojeto.TemplateProjetoDetalhamentoDTO;

import java.util.Date;

public record EtapaTemplateProjetoDetalhamentoDTO(Long id, EtapaTemplateDetalhamentoDTO etapa, TemplateProjetoDetalhamentoDTO templateProjeto, Integer ordemEtapa, Date createdAt, Date updatedAt) {
    public EtapaTemplateProjetoDetalhamentoDTO(EtapaProjeto etapaProjeto){
        this(etapaProjeto.getId(), new EtapaTemplateDetalhamentoDTO(etapaProjeto.getEtapa()), new TemplateProjetoDetalhamentoDTO(etapaProjeto.getProjeto()) , etapaProjeto.getOrdemEtapa(), etapaProjeto.getCreatedAt(), etapaProjeto.getUpdatedAt());
    }
}
