package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.Cabecalho;
import com.veigadealmeida.projetofinal.dto.cabecalho.CabecalhoDTO;
import com.veigadealmeida.projetofinal.dto.cabecalho.CabecalhoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.etapa.EtapaTemplateDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.CabecalhoRepository;
import com.veigadealmeida.projetofinal.repository.TemplateProjetoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CabecalhoService {

    private final CabecalhoRepository cabecalhoRepository;
    private final TemplateProjetoRepository templateProjetoRepository;

    public CabecalhoService(CabecalhoRepository cabecalhoRepository, TemplateProjetoRepository templateProjetoRepository) {
        this.cabecalhoRepository = cabecalhoRepository;
        this.templateProjetoRepository = templateProjetoRepository;
    }

    @Transactional
    public CabecalhoDetalhamentoDTO cadastrarCabecalho(CabecalhoDTO cabecalhoDTO) {
        Cabecalho cabecalho = new Cabecalho(cabecalhoDTO);
        cabecalho.setCadastro(cabecalho);
        cabecalho.setTemplateProjeto(templateProjetoRepository.findByIdAndAtivo(cabecalhoDTO.templateProjetoId(), true));
        cabecalho = cabecalhoRepository.save(cabecalho);
        return new CabecalhoDetalhamentoDTO(cabecalho);
    }

    public Page listarCabecalho(Pageable paginacao) {
        var page = cabecalhoRepository.findAll(paginacao).map(CabecalhoDetalhamentoDTO::new);
        return page;
    }

    public CabecalhoDetalhamentoDTO listarPorId(Long id) {
        var cabecalho = cabecalhoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Id pre-projeto fornecido não encontrado: " + id + "."));;
        return new CabecalhoDetalhamentoDTO(cabecalho);
    }
}
