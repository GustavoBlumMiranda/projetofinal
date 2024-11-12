package com.veigadealmeida.projetofinal.domain;


import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "pergunta")
@Entity(name="Pergunta")
public class Pergunta extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricaoPergunta;

    @ManyToOne
    @JoinColumn(name = "tipo_resposta_id")
    private TipoResposta tipoResposta;

    private Long idProximaPergunta;

    private Boolean ativo;

    public Pergunta(PerguntaDTO perguntaDTO){
        this.descricaoPergunta = perguntaDTO.descricaoPergunta();
        this.idProximaPergunta = perguntaDTO.idProximaPergunta();
        this.ativo = perguntaDTO.ativo();
    }

  }
