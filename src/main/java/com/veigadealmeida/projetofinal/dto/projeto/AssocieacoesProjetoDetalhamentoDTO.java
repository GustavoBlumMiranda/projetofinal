package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.Projeto;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDetalhamentoDTO;

import java.util.List;
import java.util.stream.Collectors;

public record AssocieacoesProjetoDetalhamentoDTO(Long idProjeto, String tituloProjeto, List<UsuarioDetalhamentoDTO> usuariopsAssociados) {
    public AssocieacoesProjetoDetalhamentoDTO(Projeto projeto){
        this(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getUsuarios().stream()
                        .map(usu -> new UsuarioDetalhamentoDTO(usu))
                        .collect(Collectors.toList())
        );
    }
}
