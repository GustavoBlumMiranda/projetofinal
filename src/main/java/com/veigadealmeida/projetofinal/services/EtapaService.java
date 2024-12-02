package com.veigadealmeida.projetofinal.services;


import com.veigadealmeida.projetofinal.controller.customexceptions.EntityNotFoundException;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.etapa.AlteraEtapaDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaCadastroDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.pergunta.PerguntaCadastroDTO;
import com.veigadealmeida.projetofinal.repository.EtapaEmUsoRepository;
import com.veigadealmeida.projetofinal.repository.EtapaRepository;
import com.veigadealmeida.projetofinal.repository.PerguntaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class EtapaService {

   private final EtapaRepository etapaRepository;
   private final EtapaEmUsoRepository etapaEmUsoRepository;

    public EtapaService(EtapaRepository etapaRepository, EtapaEmUsoRepository etapaEmUsoRepository){
        this.etapaRepository = etapaRepository;
        this.etapaEmUsoRepository = etapaEmUsoRepository;
    }

    public EtapaDetalhamentoDTO buscar(Long id) {
        return new EtapaDetalhamentoDTO(etapaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Etapa com ID " + id + " não encontrada.")));

    }

    public Page<EtapaDetalhamentoDTO> listarEtapas(Pageable paginacao) {
        return etapaRepository.findAll(paginacao).map(EtapaDetalhamentoDTO::new);

    }

    @Transactional
    public EtapaDetalhamentoDTO editarEtapa(AlteraEtapaDTO alteraEtapaDTO) {
        Etapa etapa = etapaRepository.findById(alteraEtapaDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Etapa com ID " + alteraEtapaDTO.id() + " não encontrada."));

        etapa.setTitulo(alteraEtapaDTO.titulo());
        etapa = etapaRepository.save(etapa);

        return new EtapaDetalhamentoDTO(etapa);
    }
}
