package com.veigadealmeida.projetofinal.dto.statusetapaprojeto;

public record ContagemEtapaDTO(Long total, Long naoIniciado, Long emAndamento, Long concluido, Long cancelado) {
    public ContagemEtapaDTO(Long total, Long naoIniciado, Long emAndamento, Long concluido, Long cancelado) {
        this.total = total;
        this.naoIniciado = naoIniciado;
        this.emAndamento = emAndamento;
        this.concluido = concluido;
        this.cancelado = cancelado;
    }
}
