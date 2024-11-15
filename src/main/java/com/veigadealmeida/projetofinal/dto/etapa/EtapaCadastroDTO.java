package com.veigadealmeida.projetofinal.dto.etapa;

import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaCadastroDTO;

import java.util.List;

public record EtapaCadastroDTO(
        Long id, // ID da etapa existente (pode ser null se for uma nova)
        String titulo,
        List<PerguntaCadastroDTO> perguntas // Lista de perguntas associadas à etapa
) {}
