package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.enumerators.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "EtapaEmUso")
@Entity(name="EtapaEmUso")
public class EtapaEmUso extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UsuarioId")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "EtapaProjetoId")
    private EtapaProjeto etapaProjeto;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusEtapaEmUso")
    private StatusEnum statusEtapaEmUso;

    @OneToMany(mappedBy = "etapaEmUso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespostasEtapaEmUso> respostasEtapaEmUso;
    public List<RespostasEtapaEmUso> getRespostasEtapaEmUso() {
        return respostasEtapaEmUso != null ? respostasEtapaEmUso : List.of();
    }

    public EtapaEmUso(EtapaProjeto etapaProjeto, Usuario usuario){
        this.usuario = usuario;
        this.etapaProjeto = etapaProjeto;
        this.statusEtapaEmUso = StatusEnum.NAO_INICIADO;
    }


}
