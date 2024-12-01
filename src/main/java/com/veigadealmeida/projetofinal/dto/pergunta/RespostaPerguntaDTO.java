package com.veigadealmeida.projetofinal.dto.pergunta;

public record RespostaPerguntaDTO(Long idProjeto, Long idEtapa,Long perguntaId, String usuarioLogin,String resposta, Long idOpcaoResposta, Double respostaNumerica) {
}
