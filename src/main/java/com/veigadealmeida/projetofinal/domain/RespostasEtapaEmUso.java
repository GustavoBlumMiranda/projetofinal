package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.respostasprojeto.AlterarRespostasProjetoDTO;
import com.veigadealmeida.projetofinal.dto.respostasprojeto.RespostasProjetoDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "RespostasEtapaEmUso")
@Entity(name="RespostasEtapaEmUso")
public class RespostasEtapaEmUso extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EtapaEmUso", nullable = false)
    private EtapaEmUso etapaEmUso;

    @ManyToOne
    @JoinColumn(name = "UsuarioId", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "perguntaId", nullable = false)
    private Pergunta pergunta;

    @ManyToOne
    @JoinColumn(name = "opcaoRespostaId")
    private OpcaoResposta opcaoResposta;

    private String respostaString;

    private Float respostaNumerica;

    private Boolean respondida;

    private Boolean aprovada;

    public RespostasEtapaEmUso(RespostasProjetoDTO respostasProjetoDTO){
        this.respostaString = respostasProjetoDTO.respostaString();
        this.respostaNumerica = respostasProjetoDTO.respostaNumerica();
        this.respondida = respostasProjetoDTO.respondida();
    }

    public RespostasEtapaEmUso AtualizaRespostasProjeto(AlterarRespostasProjetoDTO respostasProjetoDTO, RespostasEtapaEmUso respostasProjeto){
        if(respostasProjetoDTO.respostaString() != null){
            respostasProjeto.setRespostaString(respostasProjetoDTO.respostaString());
        }
        if(respostasProjetoDTO.respostaNumerica() != null){
            respostasProjeto.setRespostaNumerica(respostasProjetoDTO.respostaNumerica());
        }
        if (respostasProjetoDTO.respondida() != null){
            respostasProjeto.setRespondida(respostasProjetoDTO.respondida());
        }
        return respostasProjeto;
    }

}
