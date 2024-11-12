package com.veigadealmeida.projetofinal.dto.respostasprojeto;

public record AlterarRespostasProjetoDTO(
        Long idRespostaProjeto,
        Long idProjeto,
        Long idEtapa,
        Long idUsuario,
        Long idPergunta,
        Long idOpcaoResposta,
        String respostaString,
        Float respostaNumerica,
        Boolean respondida
) {
}