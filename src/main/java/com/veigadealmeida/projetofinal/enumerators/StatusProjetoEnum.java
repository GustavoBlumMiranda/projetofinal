package com.veigadealmeida.projetofinal.enumerators;

public enum StatusProjetoEnum {

    NAO_INICIADO(1),
    EM_ANDAMENTO(2),
    CONCLUIDO(3),
    CANCELADO(4);

    private int status;

    StatusProjetoEnum(int status){
        this.status = status;
    }
}
