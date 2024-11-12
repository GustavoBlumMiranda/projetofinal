package com.veigadealmeida.projetofinal.dto.perguntaetapa;

import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EditarPerguntaDTO (@NotNull PerguntaDTO perguntaDTO, List<OpcaoRespostaDetalhamentoDTO> opcaoRespostaDetalhamentoDTOList) {}

