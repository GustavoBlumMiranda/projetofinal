package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.OpcaoResposta;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDTO;
import com.veigadealmeida.projetofinal.dto.opcaoresposta.OpcaoRespostaDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.OpcaoRespostaRepository;
import com.veigadealmeida.projetofinal.repository.PerguntaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OpcaoRespostaService {

    /*private final OpcaoRespostaRepository opcaoRespostaRepository;
    private final PerguntaRepository perguntaRepository;

    public OpcaoRespostaService(OpcaoRespostaRepository opcaoRespostaRepository, PerguntaRepository perguntaRepository){
        this.perguntaRepository = perguntaRepository;
        this.opcaoRespostaRepository = opcaoRespostaRepository;
    }

    @Transactional
    public List<OpcaoRespostaDetalhamentoDTO> cadastrarOpcoesRespostas(List<OpcaoRespostaDTO> opcaoRespostaDTOList) {

        List<OpcaoResposta> opcaoRespostaList = opcaoRespostaDTOList.stream().map(or -> {
            OpcaoResposta opcaoResposta = new OpcaoResposta(or);
            opcaoResposta.setPergunta(perguntaRepository.findById(or.perguntaID()).orElseThrow(() -> new ObjectNotFoundException("Pergunta não encontrada para o id: " + or.perguntaID())));
            Optional<Long> proximaPerguntaId = Optional.ofNullable(or.proximaPerguntaId());
            proximaPerguntaId.map(id -> perguntaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("ProximaPergunta não encontrada para o id: " + id))).ifPresent(opcaoResposta::setProximaPergunta);
            opcaoResposta.setCadastro(opcaoResposta);
            return opcaoResposta;
        }).toList();

        List<OpcaoResposta> savedOpcaoRespostaList = opcaoRespostaRepository.saveAll(opcaoRespostaList);

        return savedOpcaoRespostaList.stream().map(OpcaoRespostaDetalhamentoDTO::new).toList();
    }



    @Transactional
    public List<OpcaoRespostaDetalhamentoDTO> editarOpcoesRespostas(List<OpcaoRespostaDetalhamentoDTO> opcaoRespostaDetalhamentoDTOList) {

        List<OpcaoResposta> orList = opcaoRespostaDetalhamentoDTOList.stream().map(or -> {
            OpcaoResposta opcaoResposta = opcaoRespostaRepository.findById(or.id()).orElseThrow(() -> new ObjectNotFoundException("OpcaoResposta não encontrada para o id: " + or.id()));
            opcaoResposta.setResposta(or.resposta());
            opcaoResposta.setProximaPergunta(perguntaRepository.findById(or.proximaPerguntaId()).orElseThrow(() -> new ObjectNotFoundException("ProximaPergunta não encontrada para o id: " + or.proximaPerguntaId())));
            opcaoResposta.setPergunta(perguntaRepository.findById(or.perguntaId()).orElseThrow(() -> new ObjectNotFoundException("Pergunta não encontrada para o id: " + or.perguntaId())));
            opcaoResposta.setUpdate(opcaoResposta);
            return opcaoResposta;
        }).toList();

        List<OpcaoResposta> updatedOpcaoRespostaList = opcaoRespostaRepository.saveAll(orList);

        return updatedOpcaoRespostaList.stream().map(OpcaoRespostaDetalhamentoDTO::new).toList();
    }
    @Transactional
    public OpcaoRespostaDetalhamentoDTO cadastrarOpcaoResposta(OpcaoRespostaDTO opcaoRespostaDTO){
        OpcaoResposta opcaoResposta = new OpcaoResposta(opcaoRespostaDTO);
        opcaoResposta.setCadastro(opcaoResposta);
        opcaoResposta.setPergunta(perguntaRepository.getReferenceById(opcaoRespostaDTO.perguntaID()));
        opcaoResposta.setProximaPergunta(perguntaRepository.getReferenceById(opcaoRespostaDTO.proximaPerguntaId()));
        opcaoResposta = opcaoRespostaRepository.save(opcaoResposta);
        return new OpcaoRespostaDetalhamentoDTO(opcaoResposta);
    }*/

}
