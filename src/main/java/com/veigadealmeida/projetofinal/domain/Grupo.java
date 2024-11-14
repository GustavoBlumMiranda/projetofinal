package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.grupo.GrupoDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "Grupo")
@Entity(name="Grupo")
public class Grupo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "permissaoGrupoId")
    private PermissaoGrupo permissaoGrupo;


    private String descricaoGrupo;

    public Grupo(GrupoDTO grupoDTO){
        this.descricaoGrupo = grupoDTO.descricaoGrupo();
    }

}
