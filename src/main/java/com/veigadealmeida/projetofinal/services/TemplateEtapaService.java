package com.veigadealmeida.projetofinal.services;


import com.veigadealmeida.projetofinal.controller.customexceptions.BadRequestException;
import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.etapa.*;
import com.veigadealmeida.projetofinal.dto.perguntaetapa.AlterarOrdemPerguntaEtapaDTO;
import com.veigadealmeida.projetofinal.dto.perguntaetapa.PerguntaEtapaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TemplateEtapaService {

   /* private EtapaRepository etapaRepository;
    private PerguntaEtapaRepository perguntaEtapaRepository;
    private PerguntaRepository perguntaRepository;
    private EtapaTemplateProjetoRepository etapaTemplateProjetoRepository;
    private ProjetoRepository projetoRepository;

    public TemplateEtapaService(EtapaRepository etapaRepository, PerguntaEtapaRepository perguntaEtapaRepository,
                                PerguntaRepository perguntaRepository, EtapaTemplateProjetoRepository etapaTemplateProjetoRepository,
                                ProjetoRepository projetoRepository){
        this.etapaRepository = etapaRepository;
        this.perguntaEtapaRepository = perguntaEtapaRepository;
        this.perguntaRepository = perguntaRepository;
        this.etapaTemplateProjetoRepository = etapaTemplateProjetoRepository;
        this.projetoRepository = projetoRepository;
    }

    @Transactional
    public List<PerguntaEtapaDetalhamentoDTO> cadastrarTemplateEtapaComListaPergunta(CadastroEtapaTemplateDTO cadastroEtapaTemplateDTO) {
        Etapa etapa = new Etapa(cadastroEtapaTemplateDTO.etapaTemplateDTO());
        etapa.setCadastro(etapa);

        List<Long> idPerguntas = cadastroEtapaTemplateDTO.idPerguntas();
        *//*List<Long> perguntasEmLoop = getPerguntasEmLoop(idPerguntas);
        if (!perguntasEmLoop.isEmpty()) {
            throw new LoopException("Um loop foi detectado na ordem das perguntas. A operação foi cancelada.");
        }*//*

        etapa = etapaRepository.save(etapa);


        List<PerguntaEtapa> perguntaEtapas = new ArrayList<>();
        int ordem = 0;
        if(cadastroEtapaTemplateDTO.idPerguntas().size() == 0){
            throw new BadRequestException("Nenhuma pergunta foi selecionada para a etapa.");
        }

        for (Long idPergunta : cadastroEtapaTemplateDTO.idPerguntas()) {
            ordem++;
            PerguntaEtapa perguntaEtapa = new PerguntaEtapa();
            perguntaEtapa.setCadastro(perguntaEtapa);
            Pergunta pergunta = perguntaRepository.findByIdAndAtivo(idPergunta, true)
                    .orElseThrow(() -> new ObjectNotFoundException("Id da pergunta não encontrada: " + idPergunta + "."));
            perguntaEtapa.setEtapa(etapa);
            perguntaEtapa.setPergunta(pergunta);
            perguntaEtapa.setOrdem(ordem);
            perguntaEtapa.setPossuiControleFluxo(pergunta.getIdProximaPergunta() != null);
            perguntaEtapas.add(perguntaEtapa);
        }
        perguntaEtapas = perguntaEtapaRepository.saveAll(perguntaEtapas);

        List<PerguntaEtapaDetalhamentoDTO> listaPerguntaEtapaDetalhamentoDTO = new ArrayList<>();
        for (PerguntaEtapa perguntaEtapa : perguntaEtapas) {
            listaPerguntaEtapaDetalhamentoDTO.add(new PerguntaEtapaDetalhamentoDTO(perguntaEtapa));
        }
        return listaPerguntaEtapaDetalhamentoDTO;
    }

    @Transactional
    public Etapa ativarOudesativarEtapa(AtivarOuDesativarEtapaDTO etapaDesativarDTO){
        Etapa etapa = etapaRepository.findById(etapaDesativarDTO.id()).orElseThrow(() -> new ObjectNotFoundException("Id da etapa fornecido não encontrado: " + etapaDesativarDTO.id() + "."));
        Boolean ativar = etapa.getAtivo();
        etapa.ativarOuDesativarEtapa(etapaDesativarDTO, !ativar);
        etapa.setUpdate(etapa);
        return etapa;
    }

    @Transactional
    public List<Optional<PerguntaEtapa>> alterarOrdemPerguntaEtapa(List<AlterarOrdemPerguntaEtapaDTO> listaAlterarOrdemPerguntaEtapaDTO){
        List<Optional<PerguntaEtapa>> perguntaEtapas = new ArrayList<>();
        perguntaEtapas.clear();
        for(AlterarOrdemPerguntaEtapaDTO dto : listaAlterarOrdemPerguntaEtapaDTO){
            Optional<PerguntaEtapa> perguntaEtapa = perguntaEtapaRepository.findById(dto.getId());
            perguntaEtapa.get().setOrdem(dto.getOrdem());
            perguntaEtapa.get().setUpdate(perguntaEtapa.get());
            perguntaEtapas.add(perguntaEtapa);
        }
        return  perguntaEtapas;
    }

    @Transactional
    public EtapaTemplateDetalhamentoDTO editarTituloEtapa(EditarTituloEtapaDTO editarTituloEtapaDTO){
        Etapa etapa = etapaRepository.findById(editarTituloEtapaDTO.id()).orElseThrow(() -> new ObjectNotFoundException("Id da etapa fornecido não encontrado: " + editarTituloEtapaDTO.id() + "."));
        etapa.setUpdate(etapa);
        etapa.editarTituloEtapa(editarTituloEtapaDTO);
        return new EtapaTemplateDetalhamentoDTO(etapa);
    }

    public Page listarEtapas(Pageable paginacao) {
        var page = etapaRepository.findAll(paginacao).map(EtapaTemplateDetalhamentoDTO::new);
        return page;
    }

    public List<EtapaTemplateDetalhamentoDTO> listaTemplateEtapasPorTemplateProjeto(Long idTemplateProjeto) {
        List<EtapaProjeto> listaEtapaProjeto = etapaTemplateProjetoRepository.findAllByTemplateProjetoId(idTemplateProjeto);
        List<EtapaTemplateDetalhamentoDTO> etapaTemplateDetalhamentoDTOList = this.toEtapaTemplateDetalhamentoDTOList(listaEtapaProjeto);
        return etapaTemplateDetalhamentoDTOList;
    }

    public static List<EtapaTemplateDetalhamentoDTO> toEtapaTemplateDetalhamentoDTOList(List<EtapaProjeto> etapasTemplateProjeto) {
        return etapasTemplateProjeto.stream()
                .map(etapaTemplateProjeto -> new EtapaTemplateDetalhamentoDTO(etapaTemplateProjeto.getEtapa()))
                .collect(Collectors.toList());
    }

    public List<EtapaTemplateDetalhamentoDTO> listaTemplateEtapasPorProjeto(Long idProjeto) {
        TemplateProjeto templateProjeto = projetoRepository.findById(idProjeto)
                .map(Projeto::getTemplateProjeto)
                .orElseThrow(() -> new ObjectNotFoundException("TemplateProjeto não encontrado"));

        return listaTemplateEtapasPorTemplateProjeto(templateProjeto.getId());
    }

   *//* public List<Long> getPerguntasEmLoop(List<Long> idPerguntas) {
        List<Long> perguntasEmLoop = new ArrayList<>();
        Set<Long> idsVisitados = new HashSet<>();

        for (Long idPergunta : idPerguntas) {
            if (!idsVisitados.add(idPergunta)) {
                perguntasEmLoop.add(idPergunta);
                continue;
            }

            Pergunta pergunta = perguntaRepository.findById(idPergunta).orElse(null);
            if (pergunta != null && pergunta.getTipoResposta().getId() == 2) {
                List<OpcaoResposta> opcoesResposta = opcaoRespostaRepository.findByPergunta(pergunta);
                for (OpcaoResposta opcaoResposta : opcoesResposta) {
                    if (opcaoResposta.getProximaPergunta() != null) {
                        Long proximaPerguntaId = opcaoResposta.getProximaPergunta().getId();
                        if (!idsVisitados.add(proximaPerguntaId)) {
                            perguntasEmLoop.add(proximaPerguntaId);
                        }
                    }
                }
            }
        }
        return perguntasEmLoop;
    }*/
}
