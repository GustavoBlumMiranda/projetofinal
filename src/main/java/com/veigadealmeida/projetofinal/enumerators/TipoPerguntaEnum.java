package com.veigadealmeida.projetofinal.enumerators;

public enum TipoPerguntaEnum {
    STRING("Texto"),
    MULTIPLA_ESCOLHA("Múltipla Escolha"),
    NUMERICO("Numérico"),
    ARQUIVO("Arquivo");

    private final String descricao;

    TipoPerguntaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
