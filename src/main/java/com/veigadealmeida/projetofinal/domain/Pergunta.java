package com.veigadealmeida.projetofinal.domain;


import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import com.veigadealmeida.projetofinal.enumerators.TipoPerguntaEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoPergunta")
    private TipoPerguntaEnum tipoPergunta;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcaoResposta> opcoesResposta = new ArrayList<>();
    @OneToMany(mappedBy = "pergunta", orphanRemoval = false)
    private List<PerguntaEtapa> etapas = new ArrayList<>();


    public Pergunta(PerguntaCadastroDTO perguntaDTO){
        this.descricaoPergunta = perguntaDTO.descricaoPergunta();
        this.tipoPergunta = TipoPerguntaEnum.valueOf(perguntaDTO.tipoPergunta().toUpperCase());
        if(tipoPergunta == TipoPerguntaEnum.MULTIPLA_ESCOLHA){
            this.opcoesResposta = perguntaDTO.opcoesResposta().stream()
                    .map(opcaoRespostaDTO -> new OpcaoResposta(opcaoRespostaDTO, this))
                    .toList();
        }
    }

    public void atualizarDescricao(String novaDescricao) {
        this.descricaoPergunta = novaDescricao;
    }


}
