package com.veigadealmeida.projetofinal.dto.etapa;

import com.veigadealmeida.projetofinal.dto.pergunta.EditarPerguntaDTO;

import java.util.List;

public record EditarEtapaDTO(Long id, String titulo, List<EditarPerguntaDTO> perguntas) {
}
