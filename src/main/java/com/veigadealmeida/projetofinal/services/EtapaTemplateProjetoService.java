package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.BadRequestException;
import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.EtapaTemplateProjeto;
import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.AlterarOrdemEtapaTemplateProjetoDTO;
import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.EtapaTemplateProjetoDTO;
import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.EtapaTemplateProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.EtapaRepository;
import com.veigadealmeida.projetofinal.repository.EtapaTemplateProjetoRepository;
import com.veigadealmeida.projetofinal.repository.TemplateProjetoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EtapaTemplateProjetoService {

    private final EtapaTemplateProjetoRepository etapaTemplateProjetoRepository;
    private final EtapaRepository etapaRepository;
    private final TemplateProjetoRepository templateProjetoRepository;

    public EtapaTemplateProjetoService(EtapaTemplateProjetoRepository etapaTemplateProjetoRepository, EtapaRepository etapaRepository, TemplateProjetoRepository templateProjetoRepository){
        this.etapaTemplateProjetoRepository =etapaTemplateProjetoRepository;
        this.etapaRepository = etapaRepository;
        this.templateProjetoRepository = templateProjetoRepository;
    }

    @Transactional
    public EtapaTemplateProjetoDetalhamentoDTO cadastrarEtapaTemplateProjetoDetalhamento(EtapaTemplateProjetoDTO etapaTemplateProjetoDTO){
        EtapaTemplateProjeto etapaTemplateProjeto = new EtapaTemplateProjeto(etapaTemplateProjetoDTO);
        etapaTemplateProjeto.setCadastro(etapaTemplateProjeto);

        etapaTemplateProjeto.setEtapa(etapaRepository.findByIdAndAtivo(etapaTemplateProjetoDTO.idEtapa(), true));
        if(etapaTemplateProjeto.getEtapa() == null){
            throw new ObjectNotFoundException("Id da etapa não encontrada: " + etapaTemplateProjetoDTO.idEtapa());
        }

        etapaTemplateProjeto.setTemplateProjeto(templateProjetoRepository.findByIdAndAtivo(etapaTemplateProjetoDTO.idTemplateProjeto(), true));
        if(etapaTemplateProjeto.getTemplateProjeto() == null){
            throw new ObjectNotFoundException("Id do template não encontrado: " + etapaTemplateProjetoDTO.idTemplateProjeto());
        }

        if(etapaTemplateProjeto.getOrdemEtapa() == null) {
            throw new BadRequestException("Ordem da etapa não encontrada");
        }

        etapaTemplateProjeto = etapaTemplateProjetoRepository.save(etapaTemplateProjeto);
        return new EtapaTemplateProjetoDetalhamentoDTO(etapaTemplateProjeto);
    }

    @Transactional
    public List<Optional<EtapaTemplateProjeto>> alterarOrdemEtapaTemplateProjeto(List<AlterarOrdemEtapaTemplateProjetoDTO> listarAlterarOrdemEtapaTemplateProjetoDTO){
        List<Optional<EtapaTemplateProjeto>> listaEtapaTemplateProjeto = new ArrayList<>();
        listaEtapaTemplateProjeto.clear();
        for(AlterarOrdemEtapaTemplateProjetoDTO dto : listarAlterarOrdemEtapaTemplateProjetoDTO){
            if(dto.ordem() == null || dto.id() == null){
                throw new BadRequestException("Id ou ordem da etapa não encontrada");
            }
            Optional<EtapaTemplateProjeto> etapaTemplateProjeto = Optional.ofNullable(etapaTemplateProjetoRepository.findById(dto.id()).
                    orElseThrow(() -> new ObjectNotFoundException("Id da etapa não encontrada: " + dto.id())));
            etapaTemplateProjeto.get().setOrdemEtapa(dto.ordem());
            etapaTemplateProjeto.get().setUpdate(etapaTemplateProjeto.get());
            listaEtapaTemplateProjeto.add(etapaTemplateProjeto);
        }
        return  listaEtapaTemplateProjeto;
    }

    public Page listarEtapaTemplateProjeto(Pageable paginacao) {
        var page = etapaTemplateProjetoRepository.findAll(paginacao).map(EtapaTemplateProjetoDetalhamentoDTO::new);
        return page;
    }
}
