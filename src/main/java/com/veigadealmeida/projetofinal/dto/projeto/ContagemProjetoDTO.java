package com.veigadealmeida.projetofinal.dto.projeto;

public record ContagemProjetoDTO(Long total, Long naoIniciado, Long emAndamento, Long concluido, Long cancelado) {
    public ContagemProjetoDTO(Long total, Long naoIniciado, Long emAndamento, Long concluido, Long cancelado) {
        this.total = total;
        this.naoIniciado = naoIniciado;
        this.emAndamento = emAndamento;
        this.concluido = concluido;
        this.cancelado = cancelado;
    }
}
