package com.veigadealmeida.projetofinal.services;


import com.veigadealmeida.projetofinal.controller.customexceptions.EntityNotFoundException;
import com.veigadealmeida.projetofinal.domain.Etapa;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.PerguntaEtapa;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaCadastroDTO;
import com.veigadealmeida.projetofinal.repository.EtapaRepository;
import com.veigadealmeida.projetofinal.repository.PerguntaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class EtapaService {

   private EtapaRepository etapaRepository;
   private PerguntaRepository perguntaRepository;

    public EtapaService(EtapaRepository etapaRepository, PerguntaRepository perguntaRepository){
        this.etapaRepository = etapaRepository;
        this.perguntaRepository = perguntaRepository;

    }

    @Transactional
    public EtapaDetalhamentoDTO cadastrarEtapa(EtapaCadastroDTO etapaCadastroDTO) {
        Etapa etapa = new Etapa(etapaCadastroDTO);
        Integer ordemPergunta = 0;
        for (PerguntaCadastroDTO perguntaCadastroDTO : etapaCadastroDTO.perguntas()) {
            ordemPergunta++;
            Pergunta pergunta;

            if (perguntaCadastroDTO.id() != null) {
                pergunta = perguntaRepository.findById(perguntaCadastroDTO.id())
                        .orElseThrow(() -> new EntityNotFoundException("Pergunta com ID " + perguntaCadastroDTO.id() + " não encontrada."));
            } else {
                pergunta = new Pergunta(perguntaCadastroDTO);
            }

            PerguntaEtapa perguntaEtapa = new PerguntaEtapa(etapa, pergunta, ordemPergunta);
            etapa.getPerguntasEtapa().add(perguntaEtapa);
            pergunta.getEtapas().add(perguntaEtapa);
        }
        etapa = etapaRepository.save(etapa);
        return new EtapaDetalhamentoDTO(etapa);
    }

    public EtapaDetalhamentoDTO buscar(Long id) {
        return new EtapaDetalhamentoDTO(etapaRepository.findById(id).get());

    }

    public Page<EtapaDetalhamentoDTO> listarEtapas(Pageable paginacao) {
        return etapaRepository.findAll(paginacao).map(EtapaDetalhamentoDTO::new);

    }


    /*@Transactional
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
