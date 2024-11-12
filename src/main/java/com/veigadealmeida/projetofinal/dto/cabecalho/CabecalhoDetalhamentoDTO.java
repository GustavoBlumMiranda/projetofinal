package com.veigadealmeida.projetofinal.dto.cabecalho;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.veigadealmeida.projetofinal.domain.Cabecalho;
import com.veigadealmeida.projetofinal.dto.templateprojeto.TemplateProjetoDetalhamentoDTO;

import java.util.Date;

public record CabecalhoDetalhamentoDTO(
        Long id,
        String estacao,
        String regional,
        String hps,
        String cep,
        String rua,
        Integer numeroFachada,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        TemplateProjetoDetalhamentoDTO templateProjeto,
        @JsonFormat(pattern = "yy-MM-dd")
        Date mesAno,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date updatedAt,
        Long version) {

    public CabecalhoDetalhamentoDTO(Cabecalho cabecalho){
        this(
                cabecalho.getId(),
                cabecalho.getEstacao(),
                cabecalho.getRegional(),
                cabecalho.getHps(),
                cabecalho.getCep(),
                cabecalho.getRua(),
                cabecalho.getNumeroFachada(),
                cabecalho.getComplemento(),
                cabecalho.getBairro(),
                cabecalho.getCidade(),
                cabecalho.getUf(),
                new TemplateProjetoDetalhamentoDTO(cabecalho.getTemplateProjeto()),
                cabecalho.getMesAno(),
                cabecalho.getCreatedAt(),
                cabecalho.getUpdatedAt(),
                cabecalho.getVersion()
        );
    }
}
