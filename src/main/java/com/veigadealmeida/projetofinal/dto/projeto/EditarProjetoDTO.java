package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.dto.etapa.EditarEtapaDTO;

import java.util.List;

public record EditarProjetoDTO(Long id, String titulo, List<EditarEtapaDTO> etapas) {
}
