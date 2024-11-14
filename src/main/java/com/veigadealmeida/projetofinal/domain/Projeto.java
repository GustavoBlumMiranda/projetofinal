package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.projeto.AlteraProjetoDTO;
import com.veigadealmeida.projetofinal.dto.projeto.ProjetoDTO;
import com.veigadealmeida.projetofinal.enumerators.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Date;

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
    private Date dataInicio;
    private Date dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusProjeto")
    private StatusEnum statusProjeto;


    @ManyToOne
    @JoinColumn(name = "usuarioCriadorId")
    private Usuario usuario;

    public Projeto(ProjetoDTO projetoDTO){
        this.titulo = projetoDTO.titulo();
        this.dataInicio = projetoDTO.dataInicio();
        this.dataFim = projetoDTO.dataFim();
    }

    public Projeto alteraProjeto(Projeto projeto, AlteraProjetoDTO alteraProjetoDTO) {
        Class<?> projetoClass = projeto.getClass();
        Class<?> dtoClass = alteraProjetoDTO.getClass();

        Field[] campos = dtoClass.getDeclaredFields();
        for (Field campo : campos) {
            try {
                Field projetoField = projetoClass.getDeclaredField(campo.getName());
                campo.setAccessible(true);
                Object valor = campo.get(alteraProjetoDTO);
                if (valor != null) {
                    projetoField.setAccessible(true);
                    projetoField.set(projeto, valor);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return projeto;
    }

}
