package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.EtapaTemplateProjetoDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "etapa_template_projeto")
@Entity(name="EtapaTemplateProjeto")
public class EtapaTemplateProjeto extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etapa_id")
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name = "template_projeto_id")
    private TemplateProjeto templateProjeto;

    private Integer ordemEtapa;

    public EtapaTemplateProjeto(EtapaTemplateProjetoDTO etapaTemplateProjetoDTO){
        this.ordemEtapa = etapaTemplateProjetoDTO.ordemEtapa();
    }

}
