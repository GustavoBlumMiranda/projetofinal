package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.cabecalho.CabecalhoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "cabecalho")
@Entity(name="cabecalho")
public class Cabecalho extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estacao;
    private String regional;
    private String hps;
    private Date mesAno;
    private String rua;
    private Integer numeroFachada;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "template_projeto_id")
    private TemplateProjeto templateProjeto;

    public Cabecalho(CabecalhoDTO cabecalhoDTO){
        this.estacao = cabecalhoDTO.estacao();
        this.regional = cabecalhoDTO.regional();
        this.hps = cabecalhoDTO.hps();
        this.mesAno = cabecalhoDTO.mesAno();
        this.rua = cabecalhoDTO.rua();
        this.numeroFachada = cabecalhoDTO.numeroFachada();
        this.complemento = cabecalhoDTO.complemento();
        this.bairro = cabecalhoDTO.bairro();
        this.cidade = cabecalhoDTO.cidade();
        this.cep = cabecalhoDTO.cep();
        this.uf = cabecalhoDTO.uf();

    }

}
