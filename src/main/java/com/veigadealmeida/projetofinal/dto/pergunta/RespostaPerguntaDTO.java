package com.veigadealmeida.projetofinal.dto.pergunta;

public record RespostaPerguntaDTO(Long etapaEmUsoId, Long perguntaId, String tiporespota, String resposta, Long idOpcaoResposta, Double respostaNumerica) {
}
