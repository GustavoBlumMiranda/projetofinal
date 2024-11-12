package com.veigadealmeida.projetofinal.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "permissao_cargo")
@Entity(name="PermissaoCargo")
public class PermissaoCargo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    private Boolean criarTemplates;
    private Boolean criarPreProjeto;
    private Boolean criarProjeto;
    private Boolean atribuirProjetos;
    private Boolean atribuirEtapas;
    private Boolean realizarUmaEtapa;
    private Boolean visualizarEtapa;
    private Boolean aprovarUmaEtapa;
    private Boolean cancelarUmProjeto;
    private Boolean gestaoDeUsuario;
    private Boolean visualizacaoCompletaDosProjetos;
    private Boolean visualizacaoDosDashboards;

}
