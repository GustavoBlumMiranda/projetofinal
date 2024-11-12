package com.veigadealmeida.projetofinal.dto.cabecalho;

import java.util.Date;

public record CabecalhoDTO(
    String estacao,
    String regional,
    String hps,
    Date mesAno,
    String rua,
    Integer numeroFachada,
    String complemento,
    String bairro,
    String cidade,
    String uf,
    String cep,
    Long templateProjetoId) {
}
