package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.respostasprojeto.*;
import com.veigadealmeida.projetofinal.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RespostasProjetoService {
    /*private final RespostasProjetoRepository respostasProjetoRepository;
    private final ProjetoRepository projetoRepository;
    private final EtapaRepository etapaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerguntaRepository perguntaRepository;
    private final OpcaoRespostaRepository opcaoRespostaRepository;
    private final StatusEtapaProjetoRepository statusEtapaProjetoRepository;
    private final PerguntaEtapaRepository perguntaEtapaRepository;
    private final StatusEtapaRepository statusEtapaRepository;
    private final StatusProjetoRepository statusProjetoRepository;

    public RespostasProjetoService(RespostasProjetoRepository respostasProjetoRepository, ProjetoRepository projetoRepository,
                                   EtapaRepository etapaRepository, UsuarioRepository usuarioRepository, PerguntaRepository perguntaRepository,
                                   OpcaoRespostaRepository opcaoRespostaRepository, StatusEtapaProjetoRepository statusEtapaProjetoRepository,
                                   PerguntaEtapaRepository perguntaEtapaRepository, StatusEtapaRepository statusEtapaRepository,
                                   StatusProjetoRepository statusProjetoRepository) {
        this.respostasProjetoRepository = respostasProjetoRepository;
        this.projetoRepository = projetoRepository;
        this.etapaRepository = etapaRepository;
        this.usuarioRepository = usuarioRepository;
        this.perguntaRepository = perguntaRepository;
        this.opcaoRespostaRepository = opcaoRespostaRepository;
        this.statusEtapaProjetoRepository = statusEtapaProjetoRepository;
        this.perguntaEtapaRepository = perguntaEtapaRepository;
        this.statusEtapaRepository = statusEtapaRepository;
        this.statusProjetoRepository = statusProjetoRepository;
    }

    @Transactional
    public RespostasProjetoDetalhamentoDTO cadastrarRespostasProjeto(RespostasProjetoDTO respostasProjetoDTO) {
       *//* RespostasProjeto  respostasProjeto = new RespostasProjeto(respostasProjetoDTO);
        respostasProjeto.setProjeto(projetoRepository.findById(respostasProjetoDTO.idProjeto()).orElseThrow(() -> new ObjectNotFoundException("Projeto com id fornecido não encontrada: " + respostasProjetoDTO.idProjeto())));
        respostasProjeto.setEtapa(etapaRepository.findById(respostasProjetoDTO.idEtapa()).orElseThrow(() -> new ObjectNotFoundException("Etapa com id fornecido não encontrada: " + respostasProjetoDTO.idEtapa())));
        respostasProjeto.setUsuario(usuarioRepository.findById(respostasProjetoDTO.idUsuario()).orElseThrow(() -> new ObjectNotFoundException("Usuario com id fornecido não encontrada: " + respostasProjetoDTO.idUsuario())));
        respostasProjeto.setPergunta(perguntaRepository.findById(respostasProjetoDTO.idPergunta()).orElseThrow((() -> new ObjectNotFoundException("Pergunta com id fornecido não encontrada: " + respostasProjetoDTO.idPergunta()))));
        respostasProjeto.setOpcaoResposta(respostasProjetoDTO.idOpcaoResposta() != null
                ? opcaoRespostaRepository.findById(respostasProjetoDTO.idOpcaoResposta())
                .orElseThrow(() -> new ObjectNotFoundException("OpcaoResposta com id fornecido não encontrada: " + respostasProjetoDTO.idOpcaoResposta()))
                : null);

        // verifica se uma etapa ou porojeto não foi iniciado, e o inicia caso não tenha sido
        iniciaEtapaEProjeto(respostasProjeto);
        respostasProjeto.setCadastro(respostasProjeto);
        respostasProjeto = respostasProjetoRepository.save(respostasProjeto);
        return new RespostasProjetoDetalhamentoDTO(respostasProjeto);*//*
        return null;
    }

    *//*@Transactional
    public RespostasProjeto alteraSituacapResposta(Long idRespostaProjeto, Boolean aprovada) {
        RespostasProjeto respostasProjeto = respostasProjetoRepository.findById(idRespostaProjeto).orElseThrow(() -> new ObjectNotFoundException("RespostaProjeto com id fornecido não encontrada: " + idRespostaProjeto));
        respostasProjeto.setAprovada(aprovada);
        return respostasProjeto;
    }*//*

    public Page<RespostasProjetoDetalhamentoDTO> listarRespostasProjeto(Pageable paginacao) {
        return respostasProjetoRepository.findAll(paginacao).map(RespostasProjetoDetalhamentoDTO::new);
    }

    public List<RespostasPreenchidasENaoPreenchidasDTO> listarRespostasPreenchidaseVazias(Long idStatusEtapaProjeto) {
        *//*StatusEtapaProjeto statusEtapaProjeto = statusEtapaProjetoRepository.findById(idStatusEtapaProjeto).orElseThrow(() -> new ObjectNotFoundException("StatusEtapaProjeto com id fornecido não encontrada: " + idStatusEtapaProjeto));
        List<PerguntaEtapa> perguntaEtapaList = perguntaEtapaRepository.findAllByEtapa(statusEtapaProjeto.getEtapa());

        List<RespostasPreenchidasENaoPreenchidasDTO> listaRespostasPreenchidasENaoPreenchidasDTO = new ArrayList<>();
        for (PerguntaEtapa perguntaEtapa : perguntaEtapaList) {
            Optional<RespostasProjeto> respostasProjeto = respostasProjetoRepository.findByPerguntaAndEtapaAndProjeto(perguntaEtapa.getPergunta(), statusEtapaProjeto.getEtapa(), statusEtapaProjeto.getProjeto());
            List<OpcaoResposta> opcaoRespostaList = new ArrayList<>();
            List<OpcaoRespostaDetalhamentoDTO> opcoesRespostaDTO = new ArrayList<>();
            if(perguntaEtapa.getPergunta().getTipoResposta().getId() == 2L) {
                opcaoRespostaList = opcaoRespostaRepository.findByPergunta(perguntaEtapa.getPergunta());
                opcoesRespostaDTO = opcaoRespostaList.stream()
                        .map(OpcaoRespostaDetalhamentoDTO::new)
                        .collect(Collectors.toList());
            }
            if(respostasProjeto.isPresent()){
                listaRespostasPreenchidasENaoPreenchidasDTO.add(new RespostasPreenchidasENaoPreenchidasDTO(perguntaEtapa, respostasProjeto.get(), opcoesRespostaDTO));
            }else{
                listaRespostasPreenchidasENaoPreenchidasDTO.add(new RespostasPreenchidasENaoPreenchidasDTO(perguntaEtapa, new RespostasProjeto(), opcoesRespostaDTO));
            }
        }
        return listaRespostasPreenchidasENaoPreenchidasDTO;*//*
        return null;

    }

    public ContagemPerguntasDTO contarPerguntas(){
        *//*Long total = 0L;
        Long naoRespondida = 0L;
        Long pendente = 0L;
        Long aprovada = 0L;
        Long reprovada = 0L;
        total = perguntaRepository.countByAtivo(true);
        aprovada = respostasProjetoRepository.countByAprovada(true);
        reprovada = respostasProjetoRepository.countByAprovada(false);
        pendente = respostasProjetoRepository.countByAprovada(null);
        naoRespondida = total - aprovada - reprovada - pendente;
        ContagemPerguntasDTO contagemPerguntasDTO = new ContagemPerguntasDTO(total, naoRespondida, pendente, aprovada, reprovada);
        return contagemPerguntasDTO;*//*
        return null;

    }


    *//*@Transactional
    public void atualizaSituacaoEtapa(RespostasProjeto respostasProjeto) {
        //verifica se todas as respostas da etapa foram aprovadas
        List<PerguntaEtapa> perguntaEtapaList = perguntaEtapaRepository.findAllByEtapa(respostasProjeto.getEtapa());
        boolean etapaFinalizada = todasRespostasAprovadas(perguntaEtapaList, respostasProjeto.getProjeto());

        // caso tenham sido aprovadas a etapa é dada como concluída
        if (etapaFinalizada) {
            StatusEtapaProjeto statusEtapaProjeto = statusEtapaProjetoRepository.findByEtapaAndProjeto(respostasProjeto.getEtapa(), respostasProjeto.getProjeto())
                    .orElseThrow(() -> new EntityNotFoundException("StatusEtapaProjeto not found"));

            statusEtapaProjeto = atualizarStatusEtapaProjeto(statusEtapaProjeto);

            //verifica se todas as etapas do projeto foram concluídas
            List<StatusEtapaProjeto> statusEtapaProjetoList = statusEtapaProjetoRepository.findAllByProjetoId(statusEtapaProjeto.getProjeto().getId());
            boolean projetoFinalizado = todasEtapasConcluidas(statusEtapaProjetoList);

            // caso tenham sido concluídas o projeto é dado como concluído
            if (projetoFinalizado) {
                Projeto projeto = statusEtapaProjeto.getProjeto();
                atualizarStatusProjeto(projeto);
            }
        }
    }*//*

    private boolean todasRespostasAprovadas(List<PerguntaEtapa> perguntaEtapaList, Projeto projeto) {
        *//*Boolean aprovada = true;
        for(PerguntaEtapa perguntaEtapa : perguntaEtapaList){
            Optional<RespostasProjeto> respostasProjeto = respostasProjetoRepository.findByPerguntaAndEtapaAndProjeto(perguntaEtapa.getPergunta(),
                    perguntaEtapa.getEtapa(), projeto);
            if(respostasProjeto.isPresent()) {
                if (respostasProjeto.get().getAprovada() == null || !respostasProjeto.get().getAprovada() || respostasProjeto.get().getRespondida() == null) {
                    aprovada = false;
                    break;
                }
            }else{
                aprovada = false;
                break;
            }
        }
        return aprovada;*//*
        return true;

    }

    *//*private StatusEtapaProjeto atualizarStatusEtapaProjeto(StatusEtapaProjeto statusEtapaProjeto) {
        statusEtapaProjeto.setStatusEtapa(statusEtapaRepository.findById(3L)
                .orElseThrow(() -> new EntityNotFoundException("StatusEtapa not found")));
        statusEtapaProjeto.setDatafim(new Date());
        statusEtapaProjeto.setUpdate(statusEtapaProjeto);
        statusEtapaProjeto = statusEtapaProjetoRepository.save(statusEtapaProjeto);
        return statusEtapaProjeto;
    }*//*

    *//*private boolean todasEtapasConcluidas(List<StatusEtapaProjeto> statusEtapaProjetoList) {
        return statusEtapaProjetoList.stream()
                .allMatch(stp -> stp.getStatusEtapa().getId() == 3L);
    }

    private Projeto atualizarStatusProjeto(Projeto projeto) {
        projeto.setStatusProjeto(statusProjetoRepository.findById(3L)
                .orElseThrow(() -> new EntityNotFoundException("StatusProjeto not found")));
        projeto.setUpdate(projeto);
        projeto.setDataFim(new Date());
        projeto = projetoRepository.save(projeto);
        return projeto;
    }
    @Transactional
    public RespostasProjeto alteraResposta(AlterarRespostasProjetoDTO respostasProjetoDTO){
        RespostasProjeto respostasProjeto = respostasProjetoRepository.findById(respostasProjetoDTO.idRespostaProjeto()).orElseThrow(EntityNotFoundException::new);
        respostasProjeto = respostasProjeto.AtualizaRespostasProjeto(respostasProjetoDTO, respostasProjeto);
        if(respostasProjetoDTO.idProjeto() != null) {
            respostasProjeto.setProjeto(projetoRepository.findById(respostasProjetoDTO.idProjeto()).orElseThrow(EntityNotFoundException::new));
        }
        if(respostasProjetoDTO.idEtapa() != null) {
            respostasProjeto.setEtapa(etapaRepository.findById(respostasProjetoDTO.idEtapa()).orElseThrow(EntityNotFoundException::new));
        }
        if(respostasProjetoDTO.idPergunta() != null) {
            respostasProjeto.setPergunta(perguntaRepository.findById(respostasProjetoDTO.idPergunta()).orElseThrow(EntityNotFoundException::new));
        }
        if(respostasProjetoDTO.idOpcaoResposta() != null) {
            respostasProjeto.setOpcaoResposta(opcaoRespostaRepository.findById(respostasProjetoDTO.idOpcaoResposta()).orElseThrow(EntityNotFoundException::new));
        }
        if(respostasProjetoDTO.idUsuario() != null) {
        respostasProjeto.setUsuario(usuarioRepository.findById(respostasProjetoDTO.idUsuario()).orElseThrow(EntityNotFoundException::new));
        }
        respostasProjeto.setAprovada(null);
        respostasProjeto.setUpdate(respostasProjeto);
        return respostasProjeto;
    }

    public void iniciaEtapaEProjeto( RespostasProjeto respostasProjeto){
        StatusEtapaProjeto statusEtapaProjeto = statusEtapaProjetoRepository.findByEtapaAndProjeto(respostasProjeto.getEtapa(), respostasProjeto.getProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Etapa não encontrada"));
        if(statusEtapaProjeto.getStatusEtapa().getId() == 1L) {
            statusEtapaProjeto.setStatusEtapa(statusEtapaRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("StatusEtapa not found")));
            statusEtapaProjeto.setDatainicio(new Date());
            statusEtapaProjeto.setUpdate(statusEtapaProjeto);
            statusEtapaProjetoRepository.save(statusEtapaProjeto);
        }
        if(statusEtapaProjeto.getProjeto().getStatusProjeto().getId() == 1L){
            statusEtapaProjeto.getProjeto().setStatusProjeto(statusProjetoRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("StatusProjeto not found")));
            statusEtapaProjeto.getProjeto().setUpdate(statusEtapaProjeto.getProjeto());
            statusEtapaProjeto.getProjeto().setDataInicio(new Date());
            projetoRepository.save(statusEtapaProjeto.getProjeto());
        }
    }*/


}
