package com.veigadealmeida.projetofinal.enumerators;

public enum TipoUsuarioEnum {
    ADMINISTRADOR("Administrador"),
    COLABORADOR("Colaborador"),
    DEV("Dev");


    private final String descricao;

    TipoUsuarioEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
