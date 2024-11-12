package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.etapa.AtivarOuDesativarEtapaDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EditarTituloEtapaDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaTemplateDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "etapa")
@Entity(name="etapa")
public class Etapa extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private Boolean ativo;

    private String justificativa;

    public Etapa(EtapaTemplateDTO etapaTemplateDTO){
        this.titulo = etapaTemplateDTO.titulo();
        this.ativo = etapaTemplateDTO.ativo();
    }

    public void ativarOuDesativarEtapa(AtivarOuDesativarEtapaDTO ativarOuDesativarEtapaDTO, Boolean ativar){
        this.ativo = ativar;
        this.justificativa = ativarOuDesativarEtapaDTO.justificativa();
    }

    public void editarTituloEtapa(EditarTituloEtapaDTO editarTituloEtapaDTO){
        this.titulo = editarTituloEtapaDTO.titulo();
    }


}
