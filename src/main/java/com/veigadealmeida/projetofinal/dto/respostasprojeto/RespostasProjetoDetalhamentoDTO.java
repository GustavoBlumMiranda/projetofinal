package com.veigadealmeida.projetofinal.dto.respostasprojeto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.veigadealmeida.projetofinal.domain.RespostasProjeto;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaTemplateDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.projeto.ProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;

import java.util.Date;

public record RespostasProjetoDetalhamentoDTO(
        Long id,
        ProjetoDetalhamentoDTO projeto,
        EtapaTemplateDetalhamentoDTO etapa,
        UsuarioDetalhamentoDTO usuario,
        PerguntaDetalhamentoDTO pergunta,
        OpcaoRespostaDetalhamentoDTO opcaoResposta,
        String respostaString,
        Float respostaNumerica,
        Boolean respondida,
        Boolean aprovada,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date updatedAt,
        Long version){
    public RespostasProjetoDetalhamentoDTO(RespostasProjeto respostasProjeto){
        this(
                respostasProjeto.getId(),
                new ProjetoDetalhamentoDTO(respostasProjeto.getProjeto()),
                new EtapaTemplateDetalhamentoDTO(respostasProjeto.getEtapa()),
                new UsuarioDetalhamentoDTO(respostasProjeto.getUsuario()),
                new PerguntaDetalhamentoDTO(respostasProjeto.getPergunta()),
                respostasProjeto.getOpcaoResposta() != null ? new OpcaoRespostaDetalhamentoDTO(respostasProjeto.getOpcaoResposta()) : null,
                respostasProjeto.getRespostaString() != null ? respostasProjeto.getRespostaString() : null,
                respostasProjeto.getRespostaNumerica() != null ? respostasProjeto.getRespostaNumerica() : null,
                respostasProjeto.getRespondida(),
                respostasProjeto.getAprovada() != null ? respostasProjeto.getAprovada() : null,
                respostasProjeto.getCreatedAt(),
                respostasProjeto.getUpdatedAt(),
                respostasProjeto.getVersion()
        );
    }
}
