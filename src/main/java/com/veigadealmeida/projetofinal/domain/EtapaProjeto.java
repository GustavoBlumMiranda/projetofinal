package com.veigadealmeida.projetofinal.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "EtapaProjeto")
@Entity(name="EtapaProjeto")
public class EtapaProjeto extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "etapaId")
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name = "projetoId")
    private Projeto projeto;

    private Integer ordemEtapa;

    public EtapaProjeto(Projeto projeto, Etapa etapa, Integer ordem) {
        this.projeto = projeto;
        this.etapa = etapa;
        this.ordemEtapa = ordem;
    }

}
