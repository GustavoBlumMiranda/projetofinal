package com.veigadealmeida.projetofinal.services;


import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.etapatemplateprojeto.EtapaTemplateProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.templateprojeto.*;
import com.veigadealmeida.projetofinal.repository.EtapaRepository;
import com.veigadealmeida.projetofinal.repository.EtapaTemplateProjetoRepository;
import com.veigadealmeida.projetofinal.repository.TemplateProjetoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateProjetoService {

    private final TemplateProjetoRepository templateProjetoRepository;
    private final EtapaRepository etapaRepository;
    private final EtapaTemplateProjetoRepository etapaTemplateProjetoRepository;

    public TemplateProjetoService(TemplateProjetoRepository templateProjetoRepository, EtapaRepository etapaRepository, EtapaTemplateProjetoRepository etapaTemplateProjetoRepository){
        this.templateProjetoRepository = templateProjetoRepository;
        this.etapaRepository = etapaRepository;
        this.etapaTemplateProjetoRepository = etapaTemplateProjetoRepository;
    }

    @Transactional
    public TemplateProjetoDetalhamentoDTO cadastrarTemplateProjeto(TemplateProjetoDTO templateProjetoDTO){
        TemplateProjeto templateProjeto = new TemplateProjeto(templateProjetoDTO);
        templateProjeto.setCadastro(templateProjeto);
        templateProjeto = templateProjetoRepository.save(templateProjeto);
        return new TemplateProjetoDetalhamentoDTO(templateProjeto);
    }

    @Transactional
    public List<EtapaTemplateProjetoDetalhamentoDTO> cadastrarTemplateProjetoComListaEtapa(CadastroTemplateProjetoComListaEtapaDTO templateProjetoComListaEtapaDTO){
        TemplateProjeto templateProjeto = new TemplateProjeto(templateProjetoComListaEtapaDTO.templateProjetoDTO());
        templateProjeto.setCadastro(templateProjeto);
        templateProjeto = templateProjetoRepository.save(templateProjeto);
        List<EtapaTemplateProjeto> etapaTemplateProjetos = new ArrayList<>();
        int ordem = 0;
        for (Long idEtapa : templateProjetoComListaEtapaDTO.idTemplateEtapa()) {
            ordem++;
            EtapaTemplateProjeto etapaTemplateProjeto = new EtapaTemplateProjeto();
            etapaTemplateProjeto.setCadastro(etapaTemplateProjeto);
            Etapa etapa = etapaRepository.findByIdAndAtivo(idEtapa, true);
            etapaTemplateProjeto.setEtapa(etapa);
            etapaTemplateProjeto.setTemplateProjeto(templateProjeto);
            etapaTemplateProjeto.setOrdemEtapa(ordem);
            etapaTemplateProjetos.add(etapaTemplateProjeto);
        }
        etapaTemplateProjetos = etapaTemplateProjetoRepository.saveAll(etapaTemplateProjetos);

        List<EtapaTemplateProjetoDetalhamentoDTO> listaEtapaTemplateProjetoDetalhamentoDTO = new ArrayList<>();
        for (EtapaTemplateProjeto etp : etapaTemplateProjetos) {
            listaEtapaTemplateProjetoDetalhamentoDTO.add(new EtapaTemplateProjetoDetalhamentoDTO(etp));
        }
        return listaEtapaTemplateProjetoDetalhamentoDTO;
    }

    @Transactional
    public TemplateProjeto ativarInativarTemplateProjeto(AtivarDesativarTemplateProjetoDTO ativarDesativarTemplateProjetoDTO){
        TemplateProjeto templateProjeto = templateProjetoRepository.getReferenceById(ativarDesativarTemplateProjetoDTO.id());
        Boolean ativo = templateProjeto.getAtivo();
        templateProjeto.ativarOuDesativarProjeto(ativarDesativarTemplateProjetoDTO, !ativo);
        templateProjeto.setUpdate(templateProjeto);
        return templateProjeto;
    }

//    @Transactional
//    public TemplateProjetoDetalhamentoDTO editarTemplateProjetoComListaEtapa(Long id, CadastroTemplateProjetoComListaEtapaDTO templateProjetoComListaEtapaDTO){
//
//    }

    @Transactional
    public TemplateProjetoDetalhamentoDTO editarTemplateProjeto(EditarTemplateProjetoDTO editarTemplateProjetoDTO){
        TemplateProjeto templateProjeto = templateProjetoRepository.findByIdAndAtivo(editarTemplateProjetoDTO.id(), true);
        templateProjeto.alterarTemplateProjeto(editarTemplateProjetoDTO);
        templateProjeto.setUpdate(templateProjeto);
        return new TemplateProjetoDetalhamentoDTO(templateProjeto);
    }

    public Page<TemplateProjetoDetalhamentoDTO> listarTemplatesProjetos(Pageable paginacao) {
        return templateProjetoRepository.findAll(paginacao).map(TemplateProjetoDetalhamentoDTO::new);
    }

    @Transactional
    public TemplateProjeto getTemplateProjetoById(Long id, Boolean ativo) {
        return templateProjetoRepository.findByIdAndAtivo(id, ativo);
    }

}
