package com.veigadealmeida.projetofinal.dto.usuario;


import com.veigadealmeida.projetofinal.domain.Usuario;

import java.util.Date;

public record UsuarioDetalhamentoDTO(Long id, String username, String nome, String email, Boolean active, String cargo, String sobrenome, String empresa, String cidade, String estado, Date nascimento) {

    public UsuarioDetalhamentoDTO(Usuario usuario){
        this(usuario.getId(), usuario.getLogin(), usuario.getNome(), usuario.getEmail(), usuario.getAtivo(), usuario.getCargo().getDescricaoCargo(), usuario.getSobrenome(), usuario.getEmpresa(), usuario.getCidade(), usuario.getEstado(), usuario.getNascimento());
    }

}
