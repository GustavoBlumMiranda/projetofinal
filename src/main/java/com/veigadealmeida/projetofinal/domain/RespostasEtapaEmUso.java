package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.pergunta.RespostaPerguntaDTO;
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
    @JoinColumn(name = "perguntaId", nullable = false)
    private Pergunta pergunta;

    @ManyToOne
    @JoinColumn(name = "opcaoRespostaId")
    private OpcaoResposta opcaoResposta;

    private String respostaString;

    private Double respostaNumerica;

    private Boolean respondida;

    private String perguntaOriginal ;

    private String respostaOriginal ;

    public RespostasEtapaEmUso(Pergunta pergunta, EtapaEmUso etapaEmUso, RespostaPerguntaDTO respostaPerguntaDTO, OpcaoResposta opcaoResposta) {
        this.pergunta = pergunta;
        this.etapaEmUso = etapaEmUso;
        this.respostaString = respostaPerguntaDTO.resposta();
        this.respostaNumerica = respostaPerguntaDTO.respostaNumerica();
        this.opcaoResposta = opcaoResposta;
        perguntaOriginal = pergunta.getDescricaoPergunta();
        respondida = true;
        if (this.respostaString != null && !this.respostaString.isEmpty()) {
            this.respostaOriginal = this.respostaString;
        } else if (this.respostaNumerica != null) {
            this.respostaOriginal = this.respostaNumerica.toString();
        } else if (this.opcaoResposta != null) {
            this.respostaOriginal = this.opcaoResposta.getResposta();
        }
    }

}
