package com.veigadealmeida.projetofinal.dto.cabecalho;

public record EnderecoDTO(String cep, String logradouro,
                          String complemento, String bairro,
                          String localidade, String uf) {
}

