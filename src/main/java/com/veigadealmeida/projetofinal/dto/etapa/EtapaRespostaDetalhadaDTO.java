package com.veigadealmeida.projetofinal.dto.etapa;

import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaRespostaDetalhadaDTO;

import java.util.List;

public record EtapaRespostaDetalhadaDTO(
        Long idEtapa,
        String tituloEtapa,
        List<PerguntaRespostaDetalhadaDTO> perguntas
) {}
