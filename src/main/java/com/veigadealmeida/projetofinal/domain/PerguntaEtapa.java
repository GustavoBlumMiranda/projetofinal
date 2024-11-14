package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.perguntaetapa.AssociaPerguntaEtapaDTO;
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

    @ManyToOne
    @JoinColumn(name = "perguntaId")
    private Pergunta pergunta;

    private Integer ordem;

    public PerguntaEtapa(AssociaPerguntaEtapaDTO perguntaEtapaDTO){
        this.ordem =perguntaEtapaDTO.ordem();
    }

}
