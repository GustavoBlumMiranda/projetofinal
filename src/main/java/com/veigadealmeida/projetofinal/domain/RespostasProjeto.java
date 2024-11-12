package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.respostasprojeto.AlterarRespostasProjetoDTO;
import com.veigadealmeida.projetofinal.dto.respostasprojeto.RespostasProjetoDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "respostas_projeto")
@Entity(name="RespostasProjeto")
public class RespostasProjeto extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "etapa_id", nullable = false)
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    @ManyToOne
    @JoinColumn(name = "opcao_resposta_id")
    private OpcaoResposta opcaoResposta;

    private String respostaString;

    private Float respostaNumerica;

    private Boolean respondida;

    private Boolean aprovada;

    public RespostasProjeto(RespostasProjetoDTO respostasProjetoDTO){
        this.respostaString = respostasProjetoDTO.respostaString();
        this.respostaNumerica = respostasProjetoDTO.respostaNumerica();
        this.respondida = respostasProjetoDTO.respondida();
    }

    public RespostasProjeto AtualizaRespostasProjeto(AlterarRespostasProjetoDTO respostasProjetoDTO, RespostasProjeto respostasProjeto){
        if(respostasProjetoDTO.respostaString() != null){
            respostasProjeto.setRespostaString(respostasProjetoDTO.respostaString());
        }
        if(respostasProjetoDTO.respostaNumerica() != null){
            respostasProjeto.setRespostaNumerica(respostasProjetoDTO.respostaNumerica());
        }
        if (respostasProjetoDTO.respondida() != null){
            respostasProjeto.setRespondida(respostasProjetoDTO.respondida());
        }
        return respostasProjeto;
    }

}
