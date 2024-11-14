package com.veigadealmeida.projetofinal.dto.pergunta;

import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDTO;

import java.util.List;

public record PerguntaDTO(String descricaoPergunta, String tipoPergunta, List<OpcaoRespostaDTO> opcoesResposta) {
}
