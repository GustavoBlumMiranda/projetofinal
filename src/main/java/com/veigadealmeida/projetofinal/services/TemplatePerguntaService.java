package com.veigadealmeida.projetofinal.services;



import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.PerguntaEtapa;
import com.veigadealmeida.projetofinal.domain.TipoResposta;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaEmUsoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TemplatePerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final TipoRespostaRepository tipoRespostaRepository;
    private final PerguntaEtapaRepository perguntaEtapaRepository;
    private final EtapaRepository etapaRepository;

    private final RespostasProjetoRepository respostasProjetoRepository;
    private final ProjetoRepository projetoRepository;

    public TemplatePerguntaService(PerguntaRepository perguntaRepository, TipoRespostaRepository tipoRespostaRepository,
                                   PerguntaEtapaRepository perguntaEtapaRepository, EtapaRepository etapaRepository,
                                   RespostasProjetoRepository respostasProjetoRepository,ProjetoRepository projetoRepository) {
        this.perguntaRepository = perguntaRepository;
        this.tipoRespostaRepository = tipoRespostaRepository;
        this.perguntaEtapaRepository = perguntaEtapaRepository;
        this.etapaRepository = etapaRepository;
        this.respostasProjetoRepository = respostasProjetoRepository;
        this.projetoRepository = projetoRepository;
    }

    @Transactional
    public PerguntaDetalhamentoDTO cadastrarPergunta(PerguntaDTO perguntaDTO) {
        Pergunta pergunta = new Pergunta(perguntaDTO);

        pergunta.setCadastro(pergunta);
        TipoResposta tipoResposta = tipoRespostaRepository.findById(perguntaDTO.tipoRespostaId())
                .orElseThrow(() -> new ObjectNotFoundException("Tipo de resposta não encontrado"));
        pergunta.setTipoResposta(tipoResposta);
        Long idProximPergunta = perguntaDTO.idProximaPergunta();
        if(idProximPergunta != null) {
            Pergunta proximaPergunta = perguntaRepository.findById(perguntaDTO.idProximaPergunta())
            .orElseThrow(() -> new ObjectNotFoundException("Id da proxima pergunta não encontrado"));
            pergunta.setIdProximaPergunta(proximaPergunta.getId());
        }else{
            pergunta.setIdProximaPergunta(null);
        }


        pergunta = perguntaRepository.save(pergunta);
        return new PerguntaDetalhamentoDTO(pergunta);
    }

    @Transactional
    public Pergunta habilitarPergunta(Long id) {
        Optional<Pergunta> perguntaOptional = perguntaRepository.findById(id);
        if (perguntaOptional.isPresent()) {
            Pergunta pergunta = perguntaOptional.get();
            pergunta.setAtivo(!pergunta.getAtivo());
            pergunta = perguntaRepository.save(pergunta);
            return pergunta;
        } else {
            throw new ObjectNotFoundException("Não foi encontrada pergunta com id " + id);
        }
    }

    @Transactional
    public PerguntaDetalhamentoDTO editarPergunta(Long id, PerguntaDTO perguntaDTO) {
        Optional<Pergunta> perguntaOptional = perguntaRepository.findById(id);
        if (perguntaOptional.isPresent()) {
            Pergunta pergunta = perguntaOptional.get();
            if (perguntaDTO.descricaoPergunta() != null) {
                pergunta.setDescricaoPergunta(perguntaDTO.descricaoPergunta());
            }
            if (perguntaDTO.idProximaPergunta() != null) {
                pergunta.setIdProximaPergunta(perguntaDTO.idProximaPergunta());
            }
            if (perguntaDTO.tipoRespostaId() != null) {
                pergunta.setTipoResposta(tipoRespostaRepository.getReferenceById(perguntaDTO.tipoRespostaId()));
            }
            if (perguntaDTO.ativo() != null) {
                pergunta.setAtivo(perguntaDTO.ativo());
            }
            /*if (this.verificaLoop(pergunta, new ArrayList<Long>())) {
                throw new LoopException("Um loop foi detectado na ordem das perguntas. A operação foi cancelada");}*/
            pergunta = perguntaRepository.save(pergunta);
            return new PerguntaDetalhamentoDTO(pergunta);
        } else {
            throw new ObjectNotFoundException("Não foi encontrada pergunta com id " + id);
        }
    }

    public Page<PerguntaDetalhamentoDTO> listarPerguntas(Pageable paginacao) {
        return perguntaRepository.findAll(paginacao)
                .map(pergunta -> {
                    Long id = pergunta.getId();
                    String descricaoPergunta = pergunta.getDescricaoPergunta()  != null ? pergunta.getDescricaoPergunta() : null;
                    String tipoResposta = pergunta.getTipoResposta() != null ? pergunta.getTipoResposta().getTipo() : null;
                    Long idProximaPergunta = pergunta.getIdProximaPergunta() != null ? pergunta.getIdProximaPergunta() : null;
                    Boolean ativo = pergunta.getAtivo() != null ? pergunta.getAtivo() : null;
                    return new PerguntaDetalhamentoDTO(id, descricaoPergunta, tipoResposta, idProximaPergunta, ativo);
                });
    }

    public List<PerguntaDetalhamentoDTO> listarPeruntasPorTemplateEtapa(Long idEtapa) {
        List<PerguntaEtapa> listaPerguntaEtapa = perguntaEtapaRepository.findAllByEtapa(etapaRepository.findById(idEtapa).get());
        List<PerguntaDetalhamentoDTO> perguntaDetalhamentoDTOList = toPerguntaDetalhamentoDTOList(listaPerguntaEtapa);
        return perguntaDetalhamentoDTOList;
    }

    public static List<PerguntaDetalhamentoDTO> toPerguntaDetalhamentoDTOList(List<PerguntaEtapa> perguntaEtapas) {
        return perguntaEtapas.stream()
                .map(perguntaEtapa -> new PerguntaDetalhamentoDTO(perguntaEtapa.getPergunta()))
                .collect(Collectors.toList());
    }

    public static List<PerguntaDetalhamentoDTO> respostasProjetoToPerguntaDetalhamentoDTOList(List<RespostasProjeto> respostasProjetos) {
        return respostasProjetos.stream()
                .map(respostaProjeto -> new PerguntaDetalhamentoDTO(respostaProjeto.getPergunta()))
                .collect(Collectors.toList());
    }

    public List<PerguntaEmUsoDetalhamentoDTO> listarPerguntasPorEtapa(Long idEtapa, Long idProjeto) {
        Projeto projeto = projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new EntityNotFoundException("Não foi encontrado projeto com id " + idProjeto));
        Etapa etapa = etapaRepository.findById(idEtapa)
                .orElseThrow(() -> new EntityNotFoundException("Não foi encontrada um template etapa com id " + idEtapa));
        List<PerguntaEtapa> perguntaEtapas = perguntaEtapaRepository.findAllByEtapa(etapa);
        List<PerguntaEmUsoDetalhamentoDTO> listaPerguntaEmUsoDetalhamentoDTO = new ArrayList<>();
        for(PerguntaEtapa perguntaEtapa: perguntaEtapas){
            Optional<RespostasProjeto> respostasProjeto = respostasProjetoRepository.findByPerguntaAndEtapaAndProjeto(perguntaEtapa.getPergunta(), etapa, projeto);
            listaPerguntaEmUsoDetalhamentoDTO.add(new PerguntaEmUsoDetalhamentoDTO(perguntaEtapa.getPergunta(), respostasProjeto.orElse(null)));
        }
        return listaPerguntaEmUsoDetalhamentoDTO;
    }


    /*public boolean verificaLoop(Pergunta pergunta, List<Long> perguntasVisitadas) {
        if (perguntasVisitadas.contains(pergunta.getId())) {
            return true;
        }

        perguntasVisitadas.add(pergunta.getId());

        if (pergunta.getIdProximaPergunta() != null) {
            Pergunta proximaPergunta = perguntaRepository.findById(pergunta.getIdProximaPergunta()).orElse(null);
            if (proximaPergunta != null && verificaLoop(proximaPergunta, new ArrayList<>(perguntasVisitadas))) {
                return true;
            }
        }

        if (pergunta.getTipoResposta().getId() == 2) {
            List<OpcaoResposta> opcoesResposta = opcaoRespostaRepository.findByPergunta(pergunta);
            for (OpcaoResposta opcaoResposta : opcoesResposta) {
                if (opcaoResposta.getProximaPergunta() != null && verificaLoop(opcaoResposta.getProximaPergunta(), new ArrayList<>(perguntasVisitadas))) {
                    return true;
                }
            }
        }

        return false;
    }*/



}
