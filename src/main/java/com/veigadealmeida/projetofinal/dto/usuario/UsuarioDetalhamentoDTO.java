package com.veigadealmeida.projetofinal.dto.usuario;


import com.veigadealmeida.projetofinal.domain.Usuario;

public record UsuarioDetalhamentoDTO(Long id, String username, String nome, String email, Boolean active, String tipoUsuario) {

    public UsuarioDetalhamentoDTO(Usuario usuario){
        this(usuario.getId(), usuario.getLogin(), usuario.getNome(), usuario.getEmail(), usuario.getAtivo(), usuario.getTipoUsuario().getDescricao());
    }

}
