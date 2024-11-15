package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.EntityNotFoundException;
import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.projeto.*;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final EtapaRepository etapaRepository;
    private final PerguntaRepository perguntaRepository;
    public ProjetoService(ProjetoRepository projetoRepository, EtapaRepository etapaRepository, PerguntaRepository perguntaRepository) {
        this.projetoRepository = projetoRepository;
        this.etapaRepository = etapaRepository;
        this.perguntaRepository = perguntaRepository;
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

        projeto = projetoRepository.save(projeto);
        return new ProjetoDetalhamentoDTO(projeto);
    }





    public ProjetoDetalhamentoDTO buscar(Long id) {
        return new ProjetoDetalhamentoDTO(projetoRepository.findById(id).get());

    }

    public Page<ProjetoDetalhamentoDTO> listarProjetos(Pageable paginacao) {
        return projetoRepository.findAll(paginacao).map(ProjetoDetalhamentoDTO::new);

    }

    /*@Transactional
    public Projeto atualizarStatusProjeto(AtualizarStatusProjetoDTO atualizarStatusProjetoDTO) {
        Optional<Projeto> projetoOptional = projetoRepository.findById(atualizarStatusProjetoDTO.idProjeto());
        if(projetoOptional.isPresent()){
            Projeto projeto = projetoOptional.get();
            projeto.setStatusProjeto(statusProjetoRepository.findById(atualizarStatusProjetoDTO.idStatusProjeto())
                    .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado status de projeto projeto com o id " + atualizarStatusProjetoDTO.idStatusProjeto())));
            projeto = projetoRepository.save(projeto);
            return projeto;
        } else {
            throw new ObjectNotFoundException("Não foi encontrado nenhum projeto com o id " + atualizarStatusProjetoDTO.idProjeto());
        }
    }*/





}
