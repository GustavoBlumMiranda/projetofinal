package com.veigadealmeida.projetofinal.dto.usuario;

import java.util.Date;

public record UsuarioEditarDTO(Long id,
                               String cpf,

                               String telefone,

                               String codUsuario,

                               String login,

                               String nome,

                               String email,

                               Boolean ativo,

                               Long cargoId,

                               String sobrenome,

                               String empresa,

                               String cidade,

                               String estado,

                               Date nascimento) {
}
