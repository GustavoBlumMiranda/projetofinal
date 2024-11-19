package com.veigadealmeida.projetofinal.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "perguntaEtapa")
@Entity(name="perguntaetapa")
public class PerguntaEtapa extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etapaId")
    private Etapa etapa;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perguntaId")
    private Pergunta pergunta;

    private Integer ordem;

    public PerguntaEtapa(Etapa etapa, Pergunta pergunta, Integer ordem) {
        this.etapa = etapa;
        this.pergunta = pergunta;
        this.ordem = ordem;
    }

}
