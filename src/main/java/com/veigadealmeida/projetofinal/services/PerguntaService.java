package com.veigadealmeida.projetofinal.services;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.stream.Collectors;

@Service
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;

    public PerguntaService(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    @Transactional
    public PerguntaDetalhamentoDTO cadastrarPergunta(PerguntaDTO perguntaDTO) {
        Pergunta pergunta = new Pergunta(perguntaDTO);
        pergunta = perguntaRepository.save(pergunta);
        return new PerguntaDetalhamentoDTO(pergunta);
    }

    public Page<PerguntaDetalhamentoDTO> listarPerguntas(Pageable paginacao) {
        return perguntaRepository.findAll(paginacao).map(PerguntaDetalhamentoDTO::new);
    }

    public PerguntaDetalhamentoDTO buscaPergunta(Long id) {
        return new PerguntaDetalhamentoDTO(perguntaRepository.findById(id).get());
    }

    @Transactional
    public PerguntaDetalhamentoDTO atualizarPergunta(Long id, PerguntaDTO perguntaDTO) {
        Pergunta pergunta = perguntaRepository.findById(id).get();
        pergunta.setDescricaoPergunta(perguntaDTO.descricaoPergunta());
        if (perguntaDTO.opcoesResposta() != null && perguntaDTO.opcoesResposta().isEmpty()) {
            pergunta.setOpcoesResposta(
                    perguntaDTO.opcoesResposta().stream()
                            .map(opcaoRespostaDTO -> new OpcaoResposta(opcaoRespostaDTO, pergunta))
                            .collect(Collectors.toList())
            );
        }
        return new PerguntaDetalhamentoDTO(perguntaRepository.save(pergunta));
    }





}
