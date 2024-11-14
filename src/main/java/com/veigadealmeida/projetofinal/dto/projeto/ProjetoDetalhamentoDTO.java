package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.Projeto;
import com.veigadealmeida.projetofinal.dto.templateprojeto.TemplateProjetoDetalhamentoDTO;

import java.util.Date;

public record ProjetoDetalhamentoDTO(Long id, String titulo, Date dataInicio, Date dataFim,Date createdAt, Date updatedAt) {
    public ProjetoDetalhamentoDTO(Projeto projeto){
        this(projeto.getId(), projeto.getTitulo(), projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getCreatedAt(), projeto.getUpdatedAt());
    }
}
