package com.veigadealmeida.projetofinal.dto.respostasprojeto;

import java.util.List;

public record RespostasEtapaDetalhamentoDTO(String tituloEtapa, List<RespostaPerguntaDetalhamentoDTO> respostas) {
}