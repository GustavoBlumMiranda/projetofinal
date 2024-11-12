package com.veigadealmeida.projetofinal.dto.respostasprojeto;

public record ContagemPerguntasDTO(Long total, Long naoRespondida, Long pendente, Long aprovada, Long reprovada) {
    public ContagemPerguntasDTO(Long total, Long naoRespondida, Long pendente, Long aprovada, Long reprovada){
        this.total = total;
        this.naoRespondida = naoRespondida;
        this.pendente = pendente;
        this.aprovada = aprovada;
        this.reprovada = reprovada;
    }
}
