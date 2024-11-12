package com.veigadealmeida.projetofinal.dto.usuario;

public record UsuarioAlteraSenhaDTO(String senhaNova, String senhaConfirmaNovaSenha) {
    public UsuarioAlteraSenhaDTO(UsuarioEsqueceuSenhaDTO usuarioEsqueceuSenhaDTO) {
        this(usuarioEsqueceuSenhaDTO.novaSenha(), usuarioEsqueceuSenhaDTO.confirmaNovaSenha());
    }
}
