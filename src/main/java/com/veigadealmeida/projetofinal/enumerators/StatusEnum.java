package com.veigadealmeida.projetofinal.enumerators;

public enum StatusEnum {

    NAO_INICIADO("Não Iniciado"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDO("Concluido"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
