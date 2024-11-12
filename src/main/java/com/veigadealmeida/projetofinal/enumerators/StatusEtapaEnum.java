package com.veigadealmeida.projetofinal.enumerators;

public enum StatusEtapaEnum {
    NAO_ATRIBUIDA(1),
    NAO_INICIADA(2),
    EM_ANDAMENTO(3),
    PENDENTE_APROVACAO(4),
    APROVADA(5);

    private int status;

    StatusEtapaEnum(int status){
        this.status = status;
    }
}
