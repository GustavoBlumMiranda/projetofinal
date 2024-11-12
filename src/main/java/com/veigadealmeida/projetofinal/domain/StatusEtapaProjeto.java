package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.statusetapaprojeto.StatusEtapaProjetoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "status_etapa_projeto")
@Entity(name="StatusEtapaProjeto")
public class StatusEtapaProjeto extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "status_etapa_id")
    private StatusEtapa statusEtapa;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "etapa_id")
    private Etapa etapa;

    private Date datainicio;
    private Date datafim;

    public StatusEtapaProjeto(StatusEtapaProjetoDTO statusEtapaProjetoDTO){
        this.datainicio = statusEtapaProjetoDTO.dataInicio();
        this.datafim = statusEtapaProjetoDTO.dataFim();
    }

}
