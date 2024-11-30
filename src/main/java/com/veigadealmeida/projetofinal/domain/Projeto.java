package com.veigadealmeida.projetofinal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.veigadealmeida.projetofinal.dto.projeto.ProjetoCadastroDTO;
import com.veigadealmeida.projetofinal.enumerators.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "projeto")
@Entity(name="projeto")
public class Projeto extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;


    @ManyToMany
    @JoinTable(
            name = "usuarioProjeto",
            joinColumns = @JoinColumn(name = "projetoId"),
            inverseJoinColumns = @JoinColumn(name = "usuarioId")
    )
    @JsonBackReference
    private List<Usuario> usuarios = new ArrayList<>();

    public Projeto(ProjetoCadastroDTO projetoDTO) {
        this.titulo = projetoDTO.titulo();
    }

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EtapaProjeto> etapasProjeto = new ArrayList<>();

}
