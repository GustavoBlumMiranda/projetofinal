package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.BadRequestException;
import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.*;
import com.veigadealmeida.projetofinal.dto.projeto.*;
import com.veigadealmeida.projetofinal.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    /*private final ProjetoRepository projetoRepository;
    private final StatusProjetoRepository statusProjetoRepository;
    private final TemplateProjetoRepository templateProjetoRepository;
    private final StatusEtapaProjetoRepository statusEtapaProjetoRepository;
    private final StatusEtapaRepository statusEtapaRepository;
    private final EtapaTemplateProjetoRepository etapaTemplateProjetoRepository;
    private final UsuarioRepository usuarioRepository;
    public ProjetoService(ProjetoRepository projetoRepository, StatusProjetoRepository statusProjetoRepository,
                          TemplateProjetoRepository templateProjetoRepository, StatusEtapaProjetoRepository statusEtapaProjetoRepository,
                          StatusEtapaRepository statusEtapaRepository, EtapaTemplateProjetoRepository etapaTemplateProjetoRepository, UsuarioRepository usuarioRepository) {
        this.projetoRepository = projetoRepository;
        this.statusProjetoRepository = statusProjetoRepository;
        this.templateProjetoRepository = templateProjetoRepository;
        this.statusEtapaProjetoRepository = statusEtapaProjetoRepository;
        this.statusEtapaRepository = statusEtapaRepository;
        this.etapaTemplateProjetoRepository = etapaTemplateProjetoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public ProjetoDetalhamentoDTO cadastrarProjeto(ProjetoDTO projetoDTO) {
        Projeto projeto = new Projeto(projetoDTO);
        projeto.setCadastro(projeto);
        projeto.setStatusProjeto(statusProjetoRepository.findById(projetoDTO.statusProjetoId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado status de projeto projeto com o id " + projetoDTO.statusProjetoId())));
        TemplateProjeto templateProjeto = templateProjetoRepository.findByIdAndAtivo(projetoDTO.idTemplateProjeto(), true);
        if (templateProjeto == null) {
            throw new ObjectNotFoundException("Não foi encontrado um template de projeto ativo com o id " + projetoDTO.idTemplateProjeto());
        }
        projeto.setTemplateProjeto(templateProjeto);
        projeto = projetoRepository.save(projeto);
        List<StatusEtapaProjeto> listaStatusEtapaProjetos = new ArrayList<>();
        List<EtapaProjeto> etapaProjetoList = etapaTemplateProjetoRepository.findAllByTemplateProjetoId(projeto.getTemplateProjeto().getId());
        for (EtapaProjeto etp : etapaProjetoList) {
            StatusEtapaProjeto statusEtapaProjeto = new StatusEtapaProjeto();
            statusEtapaProjeto.setProjeto(projeto);
            statusEtapaProjeto.setEtapa(etp.getEtapa());
            statusEtapaProjeto.setCadastro(statusEtapaProjeto);
            statusEtapaProjeto.setStatusEtapa(statusEtapaRepository.findById(1L).get());
            listaStatusEtapaProjetos.add(statusEtapaProjeto);
        }
        listaStatusEtapaProjetos = statusEtapaProjetoRepository.saveAll(listaStatusEtapaProjetos);
        return new ProjetoDetalhamentoDTO(listaStatusEtapaProjetos.get(0).getProjeto());
    }

    @Transactional
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
    }

    public Page<ProjetoDetalhamentoDTO> listarProjetos(Pageable paginacao) {
        List<Projeto> projetos = projetoRepository.findAll(paginacao).getContent();
        List<ProjetoDetalhamentoDTO> projetoDetalhamentoDTOs = new ArrayList<>();
        for (Projeto projeto : projetos) {
            Integer totalEtapa = statusEtapaProjetoRepository.countAllByProjeto(projeto);
            Integer totalEtapaConcluida = statusEtapaProjetoRepository.countAllByProjetoAndStatusEtapa(projeto, statusEtapaRepository.findById(3L).get());
            if(totalEtapa > 0){
                projeto.setPorcentagemConcluido((float) totalEtapaConcluida / totalEtapa * 100);
            } else {
                projeto.setPorcentagemConcluido(0f);
            }
            projetoDetalhamentoDTOs.add(new ProjetoDetalhamentoDTO(projeto));
        }
        return new PageImpl<>(projetoDetalhamentoDTOs, paginacao, projetoDetalhamentoDTOs.size());
    }

    public List<StatusProjetoDetalhamentoDTO> listarStatusProjeto() {
        return statusProjetoRepository.findAll().stream().map(StatusProjetoDetalhamentoDTO::new).collect(Collectors.toList());
    }

    public ContagemProjetoDTO contarProjetos() {
        List<StatusProjeto> statusProjetoList = statusProjetoRepository.findAll();
        Long total = 0L;
        Long naoIniciado = 0L;
        Long emAndamento = 0L;
        Long concluido = 0L;
        Long cancelado = 0L;
        for(StatusProjeto statusProjeto : statusProjetoList){
            total = projetoRepository.countByStatusProjetoId(statusProjeto.getId());
            if(statusProjeto.getId() == 1L){
                naoIniciado = total;
            } else if(statusProjeto.getId() == 2L){
                emAndamento = total;
            } else if(statusProjeto.getId() == 3L){
                concluido = total;
            } else if(statusProjeto.getId() == 4L){
                cancelado = total;
            }
        }
        total = projetoRepository.count();
        ContagemProjetoDTO contagemProjetoDTO = new ContagemProjetoDTO(total, naoIniciado, emAndamento, concluido, cancelado);
        return contagemProjetoDTO;
    }

    @Transactional
    public ProjetoDetalhamentoDTO alteraProjeto(AlteraProjetoDTO alteraProjetoDTO){
        Projeto projeto = projetoRepository.findById(alteraProjetoDTO.id()).orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado nenhum projeto com o id " + alteraProjetoDTO.id()));
        return new ProjetoDetalhamentoDTO(projeto.alteraProjeto(projeto, alteraProjetoDTO));
  }
  
  @Transactional
    public AssociaCoordenadorComProjetoDTO associaCoordenadorComProjeto(AssociaCoordenadorComProjetoDTO associaCoordenadorComProjetoDTO) {
        Projeto projeto = projetoRepository.findById(associaCoordenadorComProjetoDTO.projetoId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um projeto com o id " + associaCoordenadorComProjetoDTO.projetoId()));
        Usuario usuario = usuarioRepository.findById(associaCoordenadorComProjetoDTO.usuarioId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um usuário com o id " + associaCoordenadorComProjetoDTO.usuarioId()));

        if(usuario.getCargo().getId() != 3) throw new BadRequestException("O usuário informado não é um coordenador");

        projeto.setUsuario(usuario);
        return new AssociaCoordenadorComProjetoDTO(projeto,"Coordenador associado ao projeto com sucesso");
    }*/

}
