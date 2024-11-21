package com.veigadealmeida.projetofinal.dto.respostasprojeto;

import java.util.List;

public record RespostasProjetoDetalhamentoDTO(String tituloProjeto, List<RespostasEtapaDetalhamentoDTO> etapas) {
}