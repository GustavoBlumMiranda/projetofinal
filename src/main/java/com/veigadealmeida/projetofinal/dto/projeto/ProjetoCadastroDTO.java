package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.dto.etapa.EtapaCadastroDTO;

import java.util.List;

public record ProjetoCadastroDTO(
        String titulo,
        List<EtapaCadastroDTO> etapas
) {}
