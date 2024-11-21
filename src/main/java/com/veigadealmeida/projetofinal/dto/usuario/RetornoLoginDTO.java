package com.veigadealmeida.projetofinal.dto.usuario;

import com.veigadealmeida.projetofinal.domain.Usuario;

public record RetornoLoginDTO(Long id, String username, String nome, String email, Boolean active, String tipoUsuario, String token) {

    public RetornoLoginDTO(Usuario usuario, String token) {
        this(usuario.getId(), usuario.getLogin(), usuario.getNome(), usuario.getEmail(), usuario.getAtivo(), usuario.getTipoUsuario().getDescricao(), token);
    }
}
