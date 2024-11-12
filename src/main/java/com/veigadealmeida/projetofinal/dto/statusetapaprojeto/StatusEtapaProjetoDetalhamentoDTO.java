package com.veigadealmeida.projetofinal.dto.statusetapaprojeto;

import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaTemplateDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.projeto.ProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDetalhamentoDTO;

import java.util.Date;

public record StatusEtapaProjetoDetalhamentoDTO(Long id, UsuarioDetalhamentoDTO usuarioDetalhamentoDTO, String statusEtapa, ProjetoDetalhamentoDTO projeto, EtapaTemplateDetalhamentoDTO etapa, Date dataInicio, Date dataFim) {
    public StatusEtapaProjetoDetalhamentoDTO(StatusEtapaProjeto statusEtapaProjeto) {
        this(statusEtapaProjeto.getId(),
                statusEtapaProjeto.getUsuario() != null ? new UsuarioDetalhamentoDTO(statusEtapaProjeto.getUsuario()) : null,
                statusEtapaProjeto.getStatusEtapa().getStatus().toString(),
                new ProjetoDetalhamentoDTO(statusEtapaProjeto.getProjeto()),
                new EtapaTemplateDetalhamentoDTO(statusEtapaProjeto.getEtapa()),
                statusEtapaProjeto.getDatainicio(),
                statusEtapaProjeto.getDatafim());
    }
}