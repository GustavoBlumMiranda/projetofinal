package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.Projeto;
import com.veigadealmeida.projetofinal.dto.templateprojeto.TemplateProjetoDetalhamentoDTO;

import java.util.Date;

public record ProjetoDetalhamentoDTO(Long id, String titulo, Date dataPrevistaInicio, Date dataPrevistaFim ,Date dataInicio,
                                     Date dataFim, StatusProjetoDetalhamentoDTO statusProjeto, TemplateProjetoDetalhamentoDTO templateProjeto, Float porcentagemConcluido,Date createdAt,
                                     Date updatedAt, Long version) {
    public ProjetoDetalhamentoDTO(Projeto projeto){
        this(projeto.getId(), projeto.getTitulo(), projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getDataPrevistaInicio() != null ? projeto.getDataPrevistaInicio() : null,
                projeto.getDataPrevistaFim() != null ? projeto.getDataPrevistaFim() : null,
                new StatusProjetoDetalhamentoDTO(projeto.getStatusProjeto()), new TemplateProjetoDetalhamentoDTO(projeto.getTemplateProjeto()),
                projeto.getPorcentagemConcluido() != null ? projeto.getPorcentagemConcluido() : null,
                projeto.getCreatedAt(), projeto.getUpdatedAt(), projeto.getVersion());
    }
}
