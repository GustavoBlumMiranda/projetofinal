package com.veigadealmeida.projetofinal.dto.usuario;


import com.veigadealmeida.projetofinal.domain.Usuario;

import java.util.Date;

public record UsuarioDetalhamentoDTO(Long id, String username, String nome, String email, Boolean active, String cargo) {

    public UsuarioDetalhamentoDTO(Usuario usuario){
        this(usuario.getId(), usuario.getLogin(), usuario.getNome(), usuario.getEmail(), usuario.getAtivo(), usuario.getGrupo().getDescricaoGrupo());
    }

}
