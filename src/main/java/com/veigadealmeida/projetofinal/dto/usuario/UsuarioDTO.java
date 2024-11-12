package com.veigadealmeida.projetofinal.dto.usuario;

import java.util.Date;


public record UsuarioDTO(

        String cpf,

        String telefone,

        String codUsuario,

        String login,

        String nome,

        String email,

        String password,

        Boolean ativo,

        Long cargoId,

        String sobrenome,

        String empresa,

        String cidade,

        String estado,

        Date nascimento
) {}



