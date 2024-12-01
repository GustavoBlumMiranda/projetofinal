package com.veigadealmeida.projetofinal.services;
import com.veigadealmeida.projetofinal.controller.customexceptions.EntityNotFoundException;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.AlterarOpcaoRespostaDTO;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.AlterarPerguntaDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.RespostaPerguntaDTO;
import com.veigadealmeida.projetofinal.enumerators.StatusEnum;
import com.veigadealmeida.projetofinal.enumerators.TipoUsuarioEnum;
import com.veigadealmeida.projetofinal.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final EtapaEmUsoRepository etapaEmUsoRepository;
    private final RespostasEtapaEmUsoRepository respostasEtapaEmUsoRepository;
    private final OpcaoRespostaRepository opcaoRespostaRepository;
    private final PerguntaEtapaRepository perguntaEtapaRepository;
    private final ProjetoRepository projetoRepository;
    private final EtapaProjetoRepository etapaProjetoRepository;
    private final UsuarioRepository usuarioRepository;

    public PerguntaService(PerguntaRepository perguntaRepository, EtapaEmUsoRepository etapaEmUsoRepository, RespostasEtapaEmUsoRepository respostasEtapaEmUsoRepository, OpcaoRespostaRepository opcaoRespostaRepository, PerguntaEtapaRepository perguntaEtapaRepository, ProjetoRepository projetoRepository,
                           EtapaProjetoRepository etapaProjetoRepository, UsuarioRepository usuarioRepository) {
        this.perguntaRepository = perguntaRepository;
        this.etapaEmUsoRepository = etapaEmUsoRepository;
        this.respostasEtapaEmUsoRepository = respostasEtapaEmUsoRepository;
        this.opcaoRespostaRepository = opcaoRespostaRepository;
        this.perguntaEtapaRepository = perguntaEtapaRepository;
        this.projetoRepository = projetoRepository;
        this.etapaProjetoRepository = etapaProjetoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /*@Transactional
    public PerguntaDetalhamentoDTO cadastrarPergunta(PerguntaDTO perguntaDTO) {
        Pergunta pergunta = new Pergunta(perguntaDTO);
        pergunta = perguntaRepository.save(pergunta);
        return new PerguntaDetalhamentoDTO(pergunta);
    }*/

    public Page<PerguntaDetalhamentoDTO> listarPerguntas(Pageable paginacao) {
        return perguntaRepository.findAll(paginacao).map(PerguntaDetalhamentoDTO::new);
    }

    public PerguntaDetalhamentoDTO buscaPergunta(Long id) {
        return new PerguntaDetalhamentoDTO(perguntaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pergunta com ID " + id + " não encontrada.")));
    }

    @Transactional
    public ResponseEntity<String> responderPergunta(RespostaPerguntaDTO respostaPerguntaDTO) {
        Pergunta pergunta = perguntaRepository.findById(respostaPerguntaDTO.perguntaId()).get();
        EtapaProjeto etapaProjeto = etapaProjetoRepository.findByProjetoIdAndEtapaId(respostaPerguntaDTO.idProjeto(), respostaPerguntaDTO.idEtapa());
        Usuario usuario = usuarioRepository.findById(respostaPerguntaDTO.UsuarioId()).get();
        EtapaEmUso etapaEmUso = etapaEmUsoRepository.findByEtapaProjetoAndUsuario(etapaProjeto, usuario);

        Projeto projeto = etapaProjeto.getProjeto();
        if (!projeto.getUsuarios().contains(usuario)) {
            throw new IllegalStateException("Usuário do tipo COLABORADOR não está associado a este projeto.");
        }

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


    @Transactional
    public PerguntaDetalhamentoDTO editarPergunta(AlterarPerguntaDTO alterarPerguntaDTO) {
        Pergunta pergunta = perguntaRepository.findById(alterarPerguntaDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Pergunta com ID " + alterarPerguntaDTO.id() + " não encontrada."));

        boolean usuariosAssociados = pergunta.getEtapas().stream()
                .anyMatch(perguntaEtapa -> etapaEmUsoRepository.existsByEtapaIdComUsuarioAssociado(perguntaEtapa.getEtapa().getId()));

        if (usuariosAssociados) {
            throw new IllegalStateException("Perguntas associadas a etapas com usuários não podem ser editadas.");
        }

        pergunta.setDescricaoPergunta(alterarPerguntaDTO.descricaoPergunta());

        perguntaRepository.save(pergunta);
        return new PerguntaDetalhamentoDTO(pergunta);
    }

    @Transactional
    public OpcaoRespostaDetalhamentoDTO editarOpcaoResposta(AlterarOpcaoRespostaDTO alterarOpcaoRespostaDTO) {
        OpcaoResposta opcaoResposta = opcaoRespostaRepository.findById(alterarOpcaoRespostaDTO.opcaoRespostaId())
                .orElseThrow(() -> new EntityNotFoundException("OpcaoResposta com ID " + alterarOpcaoRespostaDTO.opcaoRespostaId() + " não encontrada."));

        Pergunta pergunta = opcaoResposta.getPergunta();
        boolean usuariosAssociados = pergunta.getEtapas().stream()
                .anyMatch(perguntaEtapa -> etapaEmUsoRepository.existsByEtapaIdComUsuarioAssociado(perguntaEtapa.getEtapa().getId()));

        if (usuariosAssociados) {
            throw new IllegalStateException("Opções de resposta associadas a etapas com usuários não podem ser editadas.");
        }

        opcaoResposta.setResposta(alterarOpcaoRespostaDTO.opcaoResposta());

        opcaoRespostaRepository.save(opcaoResposta);
        return new OpcaoRespostaDetalhamentoDTO(opcaoResposta);
    }







}
