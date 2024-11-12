package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.perguntaetapa.AssociaPerguntaEtapaDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "pergunta_etapa")
@Entity(name="perguntaetapa")
public class PerguntaEtapa extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etapa_id")
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    private Integer ordem;

    private Boolean possuiControleFluxo;

    public PerguntaEtapa(AssociaPerguntaEtapaDTO perguntaEtapaDTO){
        this.ordem =perguntaEtapaDTO.ordem();
        this.possuiControleFluxo = perguntaEtapaDTO.possuiControleFluxo();
    }

}
