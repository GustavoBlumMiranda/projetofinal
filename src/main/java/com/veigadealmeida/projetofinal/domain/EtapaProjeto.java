package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.EtapaTemplateProjetoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "etapaId")
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name = "projetoId")
    private Projeto projeto;

    private Integer ordemEtapa;
    private String justificativa;

    public EtapaProjeto(Projeto projeto, Etapa etapa, Integer ordem) {
        this.projeto = projeto;
        this.etapa = etapa;
        this.ordemEtapa = ordem;
    }

}
