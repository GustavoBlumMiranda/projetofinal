package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.Projeto;
import jakarta.validation.constraints.NotNull;

public record AssociaCoordenadorComProjetoDTO(@NotNull(message = "O campo 'projetoId' não pode estar vazio") Long projetoId,
                                              @NotNull(message = "O campo 'usuarioId' não pode estar vazio") Long usuarioId,
                                              String message) {
    public AssociaCoordenadorComProjetoDTO(Projeto projeto, String message){
        this(projeto.getId(), projeto.getUsuario().getId(), message);
    }
}
