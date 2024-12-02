package com.veigadealmeida.projetofinal.dto.pergunta;


import com.veigadealmeida.projetofinal.dto.opcaoresposta.EditarOpcaoRespostaDTO;

import java.util.List;

public record EditarPerguntaDTO(
        Long id,
        String descricaoPergunta,
        List<EditarOpcaoRespostaDTO> opcoesResposta
) {}
