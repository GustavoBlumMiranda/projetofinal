package com.veigadealmeida.projetofinal.dto;

import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;

import java.util.List;

public record PerguntaOpcoesRespostasDTO(
        PerguntaDetalhamentoDTO perguntaDetalhamentoDTO,
        List<OpcaoRespostaDetalhamentoDTO> opcoesRespostas) {}