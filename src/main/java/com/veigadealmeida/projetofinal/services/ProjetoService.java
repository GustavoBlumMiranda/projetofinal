package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.configuration.security.TokenJWTService;
import com.veigadealmeida.projetofinal.controller.customexceptions.EntityNotFoundException;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaRespostaDetalhadaDTO;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaRespostaDetalhadaDTO;
import com.veigadealmeida.projetofinal.dto.projeto.*;
import com.veigadealmeida.projetofinal.enumerators.StatusEnum;
import com.veigadealmeida.projetofinal.enumerators.TipoPerguntaEnum;
import com.veigadealmeida.projetofinal.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final EtapaRepository etapaRepository;
    private final PerguntaRepository perguntaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EtapaEmUsoRepository etapaEmUsoRepository;
    private final TokenJWTService tokenJWTService;
    private final EtapaProjetoRepository etapaProjetoRepository;
    private final PerguntaEtapaRepository perguntaEtapaRepository;
    private final RespostasEtapaEmUsoRepository respostasEtapaEmUsoRepository;

    public ProjetoService(ProjetoRepository projetoRepository, EtapaRepository etapaRepository, PerguntaRepository perguntaRepository,
                          UsuarioRepository usuarioRepository,EtapaEmUsoRepository etapaEmUsoRepository, TokenJWTService tokenJWTService,
                          EtapaProjetoRepository etapaProjetoRepository, PerguntaEtapaRepository perguntaEtapaRepository, RespostasEtapaEmUsoRepository respostasEtapaEmUsoRepository) {
        this.projetoRepository = projetoRepository;
        this.etapaRepository = etapaRepository;
        this.perguntaRepository = perguntaRepository;
        this.usuarioRepository = usuarioRepository;
        this.etapaEmUsoRepository = etapaEmUsoRepository;
        this.tokenJWTService = tokenJWTService;
        this.etapaProjetoRepository = etapaProjetoRepository;
        this.perguntaEtapaRepository = perguntaEtapaRepository;
        this.respostasEtapaEmUsoRepository = respostasEtapaEmUsoRepository;
    }

    @Transactional
    public ProjetoDetalhamentoDTO cadastrarProjeto(ProjetoCadastroDTO projetoDTO) {
        Projeto projeto = new Projeto(projetoDTO);
        Integer ordemEtapa = 0;
        for (EtapaCadastroDTO etapaDTO : projetoDTO.etapas()) {
            ordemEtapa++;
            Etapa etapa;
            if (etapaDTO.id() != null) {
                etapa = etapaRepository.findById(etapaDTO.id())
                        .orElseThrow(() -> new EntityNotFoundException("Etapa com ID " + etapaDTO.id() + " não encontrada."));
            } else {
                etapa = new Etapa(etapaDTO);
            }

            EtapaProjeto etapaProjeto = new EtapaProjeto(projeto, etapa, ordemEtapa);
            projeto.getEtapasProjeto().add(etapaProjeto);
            etapa.getProjetos().add(etapaProjeto);
            if(etapaDTO.id() == null) {
                Integer ordemPergunta = 0;
                for (PerguntaCadastroDTO perguntaCadastroDTO : etapaDTO.perguntas()) {
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
            }
        }
        projeto = projetoRepository.save(projeto);
        return new ProjetoDetalhamentoDTO(projeto);
    }





    public ProjetoDetalhamentoDTO buscar(Long id) {
        return new ProjetoDetalhamentoDTO(projetoRepository.findById(id).get());

    }

    public ProjetoRespostaDetalhadaDTO buscarComResposta(Long idProjeto, Long idUsuario) {
        Projeto projeto = projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new EntityNotFoundException("Projeto com ID " + idProjeto + " não encontrado."));
        Usuario usuario;
        if(idUsuario != null){
            usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + idUsuario + " não encontrado."));
        } else {
            String subject = tokenJWTService.getSubject(TokenJWTService.getBearerTokenHeader());
            usuario = usuarioRepository.findByLogin(subject);
        }

        List<EtapaRespostaDetalhadaDTO> etapasDetalhadas = projeto.getEtapasProjeto().stream()
                .map(etapaProjeto -> {
                    String tituloEtapa = etapaProjeto.getEtapa().getTitulo();
                    Long idEtapa = etapaProjeto.getEtapa().getId();
                    // Busca a EtapaEmUso correspondente ao usuário e à etapa
                    EtapaEmUso etapaEmUso = etapaEmUsoRepository.findByUsuarioIdAndProjetoId(usuario.getId(), projeto.getId())
                            .stream()
                            .filter(e -> e.getEtapaProjeto().getId().equals(etapaProjeto.getId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Etapa não encontrada para o usuário no projeto."));

                    List<PerguntaRespostaDetalhadaDTO> perguntasDetalhadas = etapaProjeto.getEtapa().getPerguntasEtapa().stream()
                            .map(perguntaEtapa -> {
                                Pergunta pergunta = perguntaEtapa.getPergunta();
                                RespostasEtapaEmUso resposta = etapaEmUso.getRespostasEtapaEmUso().stream()
                                        .filter(r -> r.getPergunta().getId().equals(pergunta.getId()))
                                        .findFirst()
                                        .orElse(null);

                                String respostaTexto = resposta != null && resposta.getRespondida()
                                        ? resposta.getRespostaOriginal()
                                        : null;

                                boolean respondida = resposta != null && resposta.getRespondida();

                                List<OpcaoRespostaDetalhamentoDTO> opcoesResposta = pergunta.getTipoPergunta() == TipoPerguntaEnum.MULTIPLA_ESCOLHA
                                        ? pergunta.getOpcoesResposta().stream()
                                        .map(OpcaoRespostaDetalhamentoDTO::new)
                                        .toList()
                                        : List.of();

                                return new PerguntaRespostaDetalhadaDTO(
                                        pergunta.getId(),
                                        pergunta.getDescricaoPergunta(),
                                        respostaTexto,
                                        respondida,
                                        pergunta.getTipoPergunta().getDescricao(),
                                        opcoesResposta
                                );
                            })
                            .toList();

                    return new EtapaRespostaDetalhadaDTO(idEtapa, tituloEtapa, perguntasDetalhadas);
                })
                .toList();

        return new ProjetoRespostaDetalhadaDTO(projeto.getTitulo(), etapasDetalhadas);
    }


    public Page<ProjetoDetalhamentoDTO> listarProjetos(Pageable paginacao) {
        return projetoRepository.findAll(paginacao).map(ProjetoDetalhamentoDTO::new);

    }

    public Page<ProjetoDetalhamentoDTO> listarProjetosSemAssociacao(Pageable paginacao) {
        return projetoRepository.findAllByUsuariosIsNull(paginacao).map(ProjetoDetalhamentoDTO::new);
    }

    @Transactional
    public ProjetoDetalhamentoDTO editarProjeto(AlteraProjetoDTO alteraProjetoDTO) {
        Projeto projeto = projetoRepository.findById(alteraProjetoDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Projeto com ID " + alteraProjetoDTO.id() + " não encontrado."));

        projeto.setTitulo(alteraProjetoDTO.titulo());
        projetoRepository.save(projeto);
        return new ProjetoDetalhamentoDTO(projeto);
    }

    @Transactional
    public ResponseEntity<String> associarUsuarioAoProjeto(Long projetoId, Long usuarioId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new EntityNotFoundException("Projeto com ID " + projetoId + " não encontrado."));


        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + usuarioId + " não encontrado."));

        if(projeto.getUsuarios().contains(usuario)){
            throw new IllegalArgumentException("Usuário com ID " + usuarioId + " já está associado ao projeto com ID " + projetoId + ".");
        }

        if (!projeto.getUsuarios().contains(usuario)) {
            projeto.getUsuarios().add(usuario);
            usuario.getProjetos().add(projeto);
            projetoRepository.save(projeto);
        }
        List<EtapaEmUso> etapasEmUso = projeto.getEtapasProjeto().stream()
                .map(etapaProjeto -> new EtapaEmUso(etapaProjeto, usuario))
                .toList();

        etapaEmUsoRepository.saveAll(etapasEmUso);


        return ResponseEntity.ok("Usuário associado ao projeto com sucesso.");
    }

    public Page<ProjetoSimplesDetalhamentoDTO> listarProjetoPorUsuario(Pageable paginacao, Long usuarioid) {
        if (usuarioid == null) {
            String subject = tokenJWTService.getSubject(TokenJWTService.getBearerTokenHeader());
            Usuario usuario = usuarioRepository.findByLogin(subject);
            usuarioid = usuario.getId();
        }
        List<Projeto> projetosAssociados = projetoRepository.findProjetosByUsuarioId(usuarioid);
        List<ProjetoSimplesDetalhamentoDTO> retorno = new ArrayList<>();

        for (Projeto proj : projetosAssociados) {
            for (Usuario usuario : proj.getUsuarios()) {
                List<EtapaEmUso> listaEtapaEmUso = etapaEmUsoRepository.findByUsuarioIdAndProjetoId(usuario.getId(), proj.getId());
                geraDetalhamentoProjeto(retorno, proj, listaEtapaEmUso, usuario);
            }
        }
        return new PageImpl<>(retorno, paginacao, retorno.size());
    }


    public Page<ProjetoSimplesDetalhamentoDTO> acompanharProjetos(Pageable paginacao) {
        List<Projeto> projetosAssociados = projetoRepository.findAll();
        List<ProjetoSimplesDetalhamentoDTO> retorno = new ArrayList<>();

        for (Projeto proj : projetosAssociados) {
            for (Usuario usuario : proj.getUsuarios()) {
                List<EtapaEmUso> listaEtapaEmUso = etapaEmUsoRepository.findByUsuarioIdAndProjetoId(usuario.getId(), proj.getId());
                geraDetalhamentoProjeto(retorno, proj, listaEtapaEmUso, usuario);
            }
        }
        return new PageImpl<>(retorno, paginacao, retorno.size());
    }


    public void geraDetalhamentoProjeto(List<ProjetoSimplesDetalhamentoDTO> retorno, Projeto proj, List<EtapaEmUso> listaEtapaEmUso, Usuario usuario) {
        if (listaEtapaEmUso.isEmpty()) {
            return;
        }
        String status = "EM ANDAMENTO";
        int concluidos = (int) listaEtapaEmUso.stream()
                .filter(etapa -> etapa.getStatusEtapaEmUso().equals(StatusEnum.CONCLUIDO))
                .count();
        boolean todasConcluidas = listaEtapaEmUso.stream()
                .allMatch(etapa -> etapa.getStatusEtapaEmUso().equals(StatusEnum.CONCLUIDO));
        boolean primeiroNaoIniciado = listaEtapaEmUso.get(0).getStatusEtapaEmUso().equals(StatusEnum.NAO_INICIADO);
        Date dataInicio = listaEtapaEmUso.get(0).getCreatedAt();
        Date datafim = todasConcluidas ? listaEtapaEmUso.get(listaEtapaEmUso.size() - 1).getUpdatedAt() : null;
        if (todasConcluidas) {
            status = "CONCLUÍDO";
        }
        if (primeiroNaoIniciado) {
            status = "NÃO INICIADO";
            dataInicio = null;
        }
        retorno.add(new ProjetoSimplesDetalhamentoDTO(proj, status, concluidos, dataInicio, datafim, usuario));
    }



    @Transactional
    public AssocieacoesProjetoDetalhamentoDTO desassociarUsuario(Long projetoId, Long usuarioId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new EntityNotFoundException("Projeto com ID " + projetoId + " não encontrado."));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + usuarioId + " não encontrado."));

        List<EtapaEmUso> etapasEmUso = etapaEmUsoRepository.findByUsuarioIdAndProjetoId(usuarioId, projetoId);
        etapaEmUsoRepository.saveAll(
                etapasEmUso.stream()
                        .peek(etapaEmUso -> etapaEmUso.setUsuario(null))
                        .toList()
        );

        if (!projeto.getUsuarios().remove(usuario)) {
            throw new IllegalArgumentException("Usuário com ID " + usuarioId + " não está associado ao projeto com ID " + projetoId + ".");
        }

        projetoRepository.save(projeto);

        return new AssocieacoesProjetoDetalhamentoDTO(projeto);
    }

    @Transactional
    public void excluirProjeto(Long projetoId) {
        try {
            Projeto projeto = projetoRepository.findById(projetoId)
                    .orElseThrow(() -> new EntityNotFoundException("Projeto com ID " + projetoId + " não encontrado."));
            validarProjetoParaExclusao(projeto);

            for (EtapaProjeto etapaProjeto : projeto.getEtapasProjeto()) {
                Etapa etapa = etapaProjeto.getEtapa();


                List<EtapaEmUso> etapasEmUso = etapaEmUsoRepository.findAllByEtapaProjeto(etapaProjeto);
                for (EtapaEmUso etapaEmUso : etapasEmUso) {

                    respostasEtapaEmUsoRepository.deleteAll(etapaEmUso.getRespostasEtapaEmUso());
                    etapaEmUsoRepository.delete(etapaEmUso);
                }

                etapaProjetoRepository.delete(etapaProjeto);

                boolean etapaEmUsoPorOutroProjeto = etapa.getProjetos().stream()
                        .anyMatch(ep -> !ep.getProjeto().getId().equals(projetoId));

                if (!etapaEmUsoPorOutroProjeto) {
                    for (PerguntaEtapa perguntaEtapa : etapa.getPerguntasEtapa()) {
                        Pergunta pergunta = perguntaEtapa.getPergunta();

                        perguntaEtapaRepository.delete(perguntaEtapa);

                        boolean perguntaEmUso = pergunta.getEtapas().stream()
                                .anyMatch(pe -> !pe.getEtapa().getId().equals(etapa.getId()));

                        if (!perguntaEmUso) {
                            perguntaRepository.delete(pergunta);
                        }
                    }
                    etapaRepository.delete(etapa);
                }
            }

            projetoRepository.delete(projeto);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao excluir o projeto. Operação abortada.", e);
        }
    }



    @Transactional
    public void validarProjetoParaExclusao(Projeto projeto) {

        if (projeto.getUsuarios() != null && !projeto.getUsuarios().isEmpty()) {
            throw new IllegalStateException("Projetos com usuários associados não podem ser excluídos.");
        }

        boolean etapasComUsuariosAssociados = projeto.getEtapasProjeto().stream()
                .map(EtapaProjeto::getEtapa)
                .anyMatch(etapa -> etapa.getProjetos().stream()
                        .anyMatch(etapaProjeto -> etapaEmUsoRepository.existsByEtapaIdComUsuarioAssociado(etapaProjeto.getId())));

        if (etapasComUsuariosAssociados) {
            throw new IllegalStateException("Etapas associadas a outros projetos com usuários não podem ser excluídas.");
        }

        boolean perguntasComUsuariosAssociados = projeto.getEtapasProjeto().stream()
                .map(EtapaProjeto::getEtapa)
                .flatMap(etapa -> etapa.getPerguntasEtapa().stream())
                .map(PerguntaEtapa::getPergunta)
                .anyMatch(pergunta -> pergunta.getEtapas().stream()
                        .anyMatch(perguntaEtapa -> etapaEmUsoRepository.existsByEtapaIdComUsuarioAssociado(perguntaEtapa.getEtapa().getId())));

        if (perguntasComUsuariosAssociados) {
            throw new IllegalStateException("Perguntas associadas a etapas de projetos com usuários não podem ser excluídas.");
        }
    }




}
