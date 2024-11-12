package com.veigadealmeida.projetofinal.dto.templateprojeto;


import java.util.List;

public record CadastroTemplateProjetoComListaEtapaDTO(TemplateProjetoDTO templateProjetoDTO, List<Long> idTemplateEtapa) {
}
