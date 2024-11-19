package com.veigadealmeida.projetofinal.services;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.RespostaPerguntaDTO;
import com.veigadealmeida.projetofinal.enumerators.StatusEnum;
import com.veigadealmeida.projetofinal.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final EtapaEmUsoRepository etapaEmUsoRepository;
    private final RespostasEtapaEmUsoRepository respostasEtapaEmUsoRepository;
    private final OpcaoRespostaRepository opcaoRespostaRepository;
    private final PerguntaEtapaRepository perguntaEtapaRepository;
    public PerguntaService(PerguntaRepository perguntaRepository, EtapaEmUsoRepository etapaEmUsoRepository, RespostasEtapaEmUsoRepository respostasEtapaEmUsoRepository, OpcaoRespostaRepository opcaoRespostaRepository, PerguntaEtapaRepository perguntaEtapaRepository) {
        this.perguntaRepository = perguntaRepository;
        this.etapaEmUsoRepository = etapaEmUsoRepository;
        this.respostasEtapaEmUsoRepository = respostasEtapaEmUsoRepository;
        this.opcaoRespostaRepository = opcaoRespostaRepository;
        this.perguntaEtapaRepository = perguntaEtapaRepository;
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

    @Transactional
    public ResponseEntity<String> responderPergunta(RespostaPerguntaDTO respostaPerguntaDTO) {
        Pergunta pergunta = perguntaRepository.findById(respostaPerguntaDTO.perguntaId()).get();
        EtapaEmUso etapaEmUso = etapaEmUsoRepository.findById(respostaPerguntaDTO.etapaEmUsoId()).get();
        EtapaProjeto etapaProjeto = etapaEmUso.getEtapaProjeto();

        if (etapaEmUso.getStatusEtapaEmUso() == StatusEnum.NAO_INICIADO) {
            etapaEmUso.setStatusEtapaEmUso(StatusEnum.EM_ANDAMENTO);
            etapaEmUsoRepository.save(etapaEmUso);
        }

        OpcaoResposta opcaoResposta = null;
        if (respostaPerguntaDTO.idOpcaoResposta() != null) {
            opcaoResposta = opcaoRespostaRepository.findById(respostaPerguntaDTO.idOpcaoResposta()).get();
        }
        respostasEtapaEmUsoRepository.save(new RespostasEtapaEmUso(pergunta, etapaEmUso, respostaPerguntaDTO, opcaoResposta));

        List<PerguntaEtapa> perguntasEtapa = perguntaEtapaRepository.findAllByEtapa(etapaProjeto.getEtapa());
        int totalPerguntas = perguntasEtapa.size();
        int perguntasRespondidas = respostasEtapaEmUsoRepository.countRespostasEtapaEmUsoByEtapaEmUso(etapaEmUso);

        if (perguntasRespondidas == totalPerguntas) {
            etapaEmUso.setStatusEtapaEmUso(StatusEnum.CONCLUIDO);
            etapaEmUsoRepository.save(etapaEmUso);
        }

        return ResponseEntity.ok("Pergunta Respondida com sucesso!");
    }








}
