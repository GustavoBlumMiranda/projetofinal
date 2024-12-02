package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.etapa.EtapaCadastroDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "etapa", cascade = CascadeType.PERSIST)
    private List<PerguntaEtapa> perguntasEtapa = new ArrayList<>();

    @OneToMany(mappedBy = "etapa", orphanRemoval = false)
    private List<EtapaProjeto> projetos = new ArrayList<>();
    public Etapa(EtapaCadastroDTO etapaCadastroDTO){
        this.titulo = etapaCadastroDTO.nomeEtapa();
    }

    public void atualizarTitulo(String novoTitulo) {
        this.titulo = novoTitulo;
    }

}
