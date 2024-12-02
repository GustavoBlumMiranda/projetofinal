package com.veigadealmeida.projetofinal.services;
import com.veigadealmeida.projetofinal.controller.customexceptions.EntityNotFoundException;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.*;
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
        Usuario usuario = usuarioRepository.findByLogin(respostaPerguntaDTO.usuarioLogin());
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
}
