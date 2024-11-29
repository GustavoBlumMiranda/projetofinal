package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.respostasprojeto.*;
import com.veigadealmeida.projetofinal.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RespostasProjetoService {
    private final RespostasEtapaEmUsoRepository respostasEtapaEmUsoRepository;
    private final EtapaEmUsoRepository etapaEmUsoRepository;
    private final ProjetoRepository projetoRepository;

    public RespostasProjetoService(RespostasEtapaEmUsoRepository respostasEtapaEmUsoRepository, EtapaEmUsoRepository etapaEmUsoRepository, ProjetoRepository projetoRepository){
        this.respostasEtapaEmUsoRepository = respostasEtapaEmUsoRepository;
        this.etapaEmUsoRepository = etapaEmUsoRepository;
        this.projetoRepository = projetoRepository;
    }

    public RespostaPerguntaDetalhamentoDTO buscaPorId(Long idResposta){
        return new RespostaPerguntaDetalhamentoDTO(respostasEtapaEmUsoRepository.findById(idResposta)
                .orElseThrow(() -> new EntityNotFoundException("resposta com ID " + idResposta + " não encontrada.")));
    }

    public RespostasEtapaDetalhamentoDTO buscaPorEtapaEmUso(Long idEtapaEmUso){
        EtapaEmUso etapaEmUso = etapaEmUsoRepository.findById(idEtapaEmUso)
                .orElseThrow(() -> new EntityNotFoundException("EtapaEmUso com ID " + idEtapaEmUso + " não encontrada."));

        String tituloEtapa = etapaEmUso.getEtapaProjeto().getEtapa().getTitulo();

        List<RespostaPerguntaDetalhamentoDTO> respostasDetalhamentoDTO = etapaEmUso.getRespostasEtapaEmUso().stream()
                .map(RespostaPerguntaDetalhamentoDTO::new)
                .toList();

        return new RespostasEtapaDetalhamentoDTO(tituloEtapa, respostasDetalhamentoDTO);
    }

    public RespostasProjetoDetalhamentoDTO buscaPorProjetoUsuario(Long idUsuario, Long idProjeto) {
        Projeto projeto = projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new EntityNotFoundException("Projeto com ID " + idProjeto + " não encontrado."));
        String tituloProjeto = projeto.getTitulo();

        List<EtapaEmUso> etapasEmUso = etapaEmUsoRepository.findByUsuarioIdAndProjetoId(idUsuario, idProjeto);

        if (etapasEmUso.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma etapa em uso encontrada para o projeto e usuário fornecidos.");
        }

        List<RespostasEtapaDetalhamentoDTO> respostasPorEtapa = etapasEmUso.stream()
                .map(etapaEmUso -> {
                    String tituloEtapa = etapaEmUso.getEtapaProjeto().getEtapa().getTitulo();
                    List<RespostaPerguntaDetalhamentoDTO> respostasDetalhamentoDTO = etapaEmUso.getRespostasEtapaEmUso().stream()
                            .map(RespostaPerguntaDetalhamentoDTO::new)
                            .toList();
                    return new RespostasEtapaDetalhamentoDTO(tituloEtapa, respostasDetalhamentoDTO);
                })
                .toList();

        return new RespostasProjetoDetalhamentoDTO(tituloProjeto, respostasPorEtapa);
    }


}
