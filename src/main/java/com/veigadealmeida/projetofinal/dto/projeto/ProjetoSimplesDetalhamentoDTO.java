package com.veigadealmeida.projetofinal.dto.projeto;

import com.veigadealmeida.projetofinal.domain.Projeto;
import java.util.Date;

public record ProjetoSimplesDetalhamentoDTO(Long idProjeto, String titulo, String situacao, Integer totalEtapas, Integer etapasConcluidas, Date dataInicio, Date datafim){
    public ProjetoSimplesDetalhamentoDTO(Projeto projeto, String situacao, Integer etapasConcluidas, Date dataInicio, Date dataFim){
        this(projeto.getId(), projeto.getTitulo(), situacao, projeto.getEtapasProjeto().size(), etapasConcluidas, dataInicio, dataFim);
    }
}
