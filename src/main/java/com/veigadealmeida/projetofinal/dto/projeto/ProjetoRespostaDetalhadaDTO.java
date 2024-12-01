package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.dto.etapa.EtapaRespostaDetalhadaDTO;

import java.util.List;

public record ProjetoRespostaDetalhadaDTO(
        String tituloProjeto,
        List<EtapaRespostaDetalhadaDTO> etapas
) {}
