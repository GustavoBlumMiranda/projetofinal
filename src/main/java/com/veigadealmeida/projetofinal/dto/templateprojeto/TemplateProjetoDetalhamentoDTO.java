package com.veigadealmeida.projetofinal.dto.templateprojeto;

import com.veigadealmeida.projetofinal.domain.Projeto;


import java.util.Date;

public record TemplateProjetoDetalhamentoDTO(Long id, String titulo, String justificativa, Date createdAt, Date updatedAt) {
    public TemplateProjetoDetalhamentoDTO(Projeto templateProjeto){
        this(templateProjeto.getId(), templateProjeto.getTitulo(), templateProjeto.getTitulo(), templateProjeto.getCreatedAt(), templateProjeto.getUpdatedAt());
    }
}
