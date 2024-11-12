package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.Etapa;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import com.veigadealmeida.projetofinal.domain.PerguntaEtapa;
import com.veigadealmeida.projetofinal.dto.perguntaetapa.AssociaPerguntaEtapaDTO;
import com.veigadealmeida.projetofinal.dto.perguntaetapa.PerguntaEtapaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.EtapaRepository;
import com.veigadealmeida.projetofinal.repository.PerguntaEtapaRepository;
import com.veigadealmeida.projetofinal.repository.PerguntaRepository;
import org.springframework.stereotype.Service;

@Service
public class PerguntaEtapaService {

    private PerguntaEtapaRepository perguntaEtapaRepository;
    private EtapaRepository etapaRepository;
    private PerguntaRepository perguntaRepository;

    public PerguntaEtapaService(PerguntaEtapaRepository perguntaEtapaRepository, EtapaRepository etapaRepository, PerguntaRepository perguntaRepository){
        this.perguntaEtapaRepository = perguntaEtapaRepository;
        this.etapaRepository = etapaRepository;
        this.perguntaRepository = perguntaRepository;
    }

    public PerguntaEtapaDetalhamentoDTO associaPerguntaEtapa (AssociaPerguntaEtapaDTO associaPerguntaEtapaDTO){
        PerguntaEtapa perguntaEtapa = new PerguntaEtapa(associaPerguntaEtapaDTO);
        perguntaEtapa.setCadastro(perguntaEtapa);
        Pergunta pergunta = perguntaRepository.findByIdAndAtivo((associaPerguntaEtapaDTO.idPergunta()), true)
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um template de pergunta ativo com o id " + associaPerguntaEtapaDTO.idPergunta()));
        perguntaEtapa.setPergunta(pergunta);
        Etapa etapa = etapaRepository.findByIdAndAtivo(associaPerguntaEtapaDTO.idEtapa(), true);
        if (etapa == null){
            throw new ObjectNotFoundException("Não foi encontrado um template de etapa ativo com o id " + associaPerguntaEtapaDTO.idEtapa());
        }
        perguntaEtapa.setEtapa(etapa);
        perguntaEtapa = perguntaEtapaRepository.save(perguntaEtapa);
        return new PerguntaEtapaDetalhamentoDTO(perguntaEtapa);
    }

}
