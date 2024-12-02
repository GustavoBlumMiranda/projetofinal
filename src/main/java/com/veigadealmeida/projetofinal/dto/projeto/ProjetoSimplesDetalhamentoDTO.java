package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.Projeto;
import com.veigadealmeida.projetofinal.domain.Usuario;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDetalhamentoDTO;

import java.util.Date;
import java.util.List;

public record ProjetoSimplesDetalhamentoDTO(Long idProjeto, String titulo, String situacao, Integer totalEtapas, Integer etapasConcluidas, Date dataInicio, Date datafim, UsuarioDetalhamentoDTO usuario){
    public ProjetoSimplesDetalhamentoDTO(Projeto projeto, String situacao, Integer etapasConcluidas, Date dataInicio, Date dataFim, Usuario usuario){
        this(projeto.getId(),
                projeto.getTitulo(),
                situacao,
                projeto.getEtapasProjeto().size(),
                etapasConcluidas,
                dataInicio,
                dataFim,
                new UsuarioDetalhamentoDTO(usuario));
    }
}
