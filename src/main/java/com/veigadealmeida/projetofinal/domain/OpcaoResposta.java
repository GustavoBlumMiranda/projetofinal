package com.veigadealmeida.projetofinal.domain;


import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "opcao_resposta")
@Entity(name="OpcaoResposta")
public class OpcaoResposta extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String resposta;

    @ManyToOne
    @JoinColumn(name = "proxima_pergunta_id")
    private Pergunta proximaPergunta;

    @ManyToOne
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    public OpcaoResposta(OpcaoRespostaDTO opcaoRespostaDTO){
        this.resposta = opcaoRespostaDTO.opcaoResposta();
    }

}
