package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.EntityNotFoundException;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.projeto.*;
import com.veigadealmeida.projetofinal.enumerators.StatusEnum;
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
    public ProjetoService(ProjetoRepository projetoRepository, EtapaRepository etapaRepository, PerguntaRepository perguntaRepository, UsuarioRepository usuarioRepository,EtapaEmUsoRepository etapaEmUsoRepository) {
        this.projetoRepository = projetoRepository;
        this.etapaRepository = etapaRepository;
        this.perguntaRepository = perguntaRepository;
        this.usuarioRepository = usuarioRepository;
        this.etapaEmUsoRepository = etapaEmUsoRepository;
    }

    @Transactional
    public ProjetoDetalhamentoDTO cadastrarProjeto(ProjetoCadastroDTO projetoDTO) {
        Projeto projeto = new Projeto(projetoDTO); // Apenas inicializa os campos básicos do projeto
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

    public Page<ProjetoDetalhamentoDTO> listarProjetos(Pageable paginacao) {
        return projetoRepository.findAll(paginacao).map(ProjetoDetalhamentoDTO::new);

    }

    @Transactional
    public ResponseEntity<String> associarUsuarioAoProjeto(Long projetoId, Long usuarioId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new EntityNotFoundException("Projeto com ID " + projetoId + " não encontrado."));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + usuarioId + " não encontrado."));

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
        List<Projeto> projetosAssociados = projetoRepository.findProjetosByUsuarioId(usuarioid);
        List<ProjetoSimplesDetalhamentoDTO> retorno = new ArrayList<>();

        for (Projeto proj : projetosAssociados){
            List<EtapaEmUso> listaEtapaEmUso = etapaEmUsoRepository.findByUsuarioIdAndProjetoId(usuarioid, proj.getId());
            if (listaEtapaEmUso.isEmpty()) {
                continue;
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
            if(todasConcluidas){
                status = "CONCLUÍDO";
            }
            if(primeiroNaoIniciado){
                status = "NÃO INICIADO";
                dataInicio = null;
            }
            retorno.add(new ProjetoSimplesDetalhamentoDTO(proj, status, concluidos, dataInicio, datafim));
        }
        return new PageImpl<>(retorno, paginacao, retorno.size());
    }



}
