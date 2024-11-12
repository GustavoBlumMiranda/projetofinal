package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.templateprojeto.AtivarDesativarTemplateProjetoDTO;
import com.veigadealmeida.projetofinal.dto.templateprojeto.TemplateProjetoDTO;
import com.veigadealmeida.projetofinal.dto.templateprojeto.EditarTemplateProjetoDTO;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "template_projeto")
@Entity(name="TemplateProjeto")
public class TemplateProjeto extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidadeEtapas;
    private String titulo;
    private Boolean ativo;
    private String justificativa;

    public TemplateProjeto (TemplateProjetoDTO templateProjetoDTO){
        this.titulo = templateProjetoDTO.titulo();
        this.quantidadeEtapas = templateProjetoDTO.quantidadeEtapas();
        this.ativo = true;
    }

    public void ativarOuDesativarProjeto(AtivarDesativarTemplateProjetoDTO ativarDesativarTemplateProjetoDTO, Boolean ativo){
        this.justificativa = ativarDesativarTemplateProjetoDTO.justificativa();
        this.ativo = ativo;
    }

    public void alterarTemplateProjeto(EditarTemplateProjetoDTO editarTemplateProjetoDTO){
        if (editarTemplateProjetoDTO.titulo() != null){
            this.titulo = editarTemplateProjetoDTO.titulo();
        }
        if (editarTemplateProjetoDTO.quantidadeEtapas() != null){
            this.quantidadeEtapas = editarTemplateProjetoDTO.quantidadeEtapas();
        }
    }

}
