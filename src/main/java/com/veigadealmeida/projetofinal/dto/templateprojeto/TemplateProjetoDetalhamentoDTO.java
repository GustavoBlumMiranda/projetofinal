package com.veigadealmeida.projetofinal.dto.templateprojeto;

import com.veigadealmeida.projetofinal.domain.TemplateProjeto;

import java.util.Date;

public record TemplateProjetoDetalhamentoDTO(Long id, Integer quantidadeEtapas, String titulo, Boolean ativo, String justificativa, Date createdAt, Date updatedAt, Long version) {
    public TemplateProjetoDetalhamentoDTO(TemplateProjeto templateProjeto){
        this(templateProjeto.getId(), templateProjeto.getQuantidadeEtapas(), templateProjeto.getTitulo(), templateProjeto.getAtivo(), templateProjeto.getJustificativa(), templateProjeto.getCreatedAt(), templateProjeto.getUpdatedAt(), templateProjeto.getVersion());
    }
}
