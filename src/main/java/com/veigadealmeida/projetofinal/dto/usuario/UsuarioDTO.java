package com.veigadealmeida.projetofinal.dto.usuario;

import java.util.Date;


public record UsuarioDTO(

        String cpf,

        String codUsuario,

        String login,

        String nome,

        String email,

        String password,

        Boolean ativo,

        String tipoUsuario
) {}



