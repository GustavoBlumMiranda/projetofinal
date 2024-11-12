package com.veigadealmeida.projetofinal.enumerators;

public enum TipoRespostaEnum {

    STRING(1),
    MULTIPLA_ESCOLHA(2),
    NUMERICO(3),
    DATA(4);

    private int tipo;

    TipoRespostaEnum(int tipo){
        this.tipo = tipo;
    }
}
