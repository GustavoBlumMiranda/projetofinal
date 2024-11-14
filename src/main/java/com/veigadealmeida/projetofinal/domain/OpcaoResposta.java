package com.veigadealmeida.projetofinal.domain;


import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "OpcaoResposta")
@Entity(name="OpcaoResposta")
public class OpcaoResposta extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String resposta;

    @ManyToOne
    @JoinColumn(name = "perguntaId")
    private Pergunta pergunta;

    public OpcaoResposta(OpcaoRespostaDTO opcaoRespostaDTO, Pergunta pergunta) {
        this.resposta = opcaoRespostaDTO.opcaoResposta();
        this.pergunta = pergunta;
    }

}
