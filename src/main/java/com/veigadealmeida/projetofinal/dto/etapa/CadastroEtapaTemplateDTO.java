package com.veigadealmeida.projetofinal.dto.etapa;

import java.util.List;

public record CadastroEtapaTemplateDTO(EtapaTemplateDTO etapaTemplateDTO, List<Long> idPerguntas) {
}
