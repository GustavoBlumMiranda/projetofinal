package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.dto.statusetapaprojeto.AssociaTecnicoEtapaDTO;
import com.veigadealmeida.projetofinal.dto.statusetapaprojeto.ContagemEtapaDTO;
import com.veigadealmeida.projetofinal.dto.statusetapaprojeto.StatusEtapaProjetoDTO;
import com.veigadealmeida.projetofinal.dto.statusetapaprojeto.StatusEtapaProjetoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;

@Service
public class StatusEtapaProjetoService {
   /* private final StatusEtapaProjetoRepository statusEtapaProjetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final EtapaRepository etapaRepository;
    private final StatusEtapaRepository statusEtapaRepository;
    private final UsuarioService usuarioService;


    public StatusEtapaProjetoService(StatusEtapaProjetoRepository statusEtapaProjetoRepository, UsuarioRepository usuarioRepository,
                                     ProjetoRepository projetoRepository, EtapaRepository etapaRepository,
                                     StatusEtapaRepository statusEtapaRepository, UsuarioService usuarioService) {
        this.statusEtapaProjetoRepository = statusEtapaProjetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.etapaRepository = etapaRepository;
        this.statusEtapaRepository = statusEtapaRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public StatusEtapaProjetoDetalhamentoDTO cadastrarStatusEtapaProjeto(StatusEtapaProjetoDTO statusEtapaProjetoDTO) {
        StatusEtapaProjeto statusEtapaProjeto = new StatusEtapaProjeto(statusEtapaProjetoDTO);
        statusEtapaProjeto.setStatusEtapa(statusEtapaRepository.findById(statusEtapaProjetoDTO.statusEtapaId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um status de etapa com o id " + statusEtapaProjetoDTO.statusEtapaId())));
        statusEtapaProjeto.setUsuario(usuarioRepository.findById(statusEtapaProjetoDTO.usuarioId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um usuario com o id " + statusEtapaProjetoDTO.usuarioId())));
        statusEtapaProjeto.setProjeto(projetoRepository.findById(statusEtapaProjetoDTO.projetoId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um projeto com o id " + statusEtapaProjetoDTO.projetoId())));
        statusEtapaProjeto.setEtapa(etapaRepository.findById(statusEtapaProjetoDTO.etapaId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um template etapa com o id " + statusEtapaProjetoDTO.etapaId())));
        statusEtapaProjeto.setCadastro(statusEtapaProjeto);
        statusEtapaProjeto = statusEtapaProjetoRepository.save(statusEtapaProjeto);
        return new StatusEtapaProjetoDetalhamentoDTO(statusEtapaProjeto);
    }

    public Page<StatusEtapaProjetoDetalhamentoDTO> listarStatusEtapaProjeto(Pageable paginacao) {
        return statusEtapaProjetoRepository.findAll(paginacao).map(StatusEtapaProjetoDetalhamentoDTO::new);
    }

*//*    public Page<StatusEtapaDetalhamentoDTO> listarStatusEtapa(Pageable paginacao) {
        return statusEtapaRepository.findAll(paginacao).map(StatusEtapaDetalhamentoDTO::new);
    }*//*

    public List<StatusEtapaProjetoDetalhamentoDTO> listarStatusEtapaProjetoPorProjeto(Long idProjeto) {
        return statusEtapaProjetoRepository.findAllByProjetoId(idProjeto).stream().map(StatusEtapaProjetoDetalhamentoDTO::new).toList();
    }

    public ContagemEtapaDTO contarEtapas() {
        List<StatusEtapa> statusEtapaList = statusEtapaRepository.findAll();
        Long total = 0L;
        Long naoIniciado = 0L;
        Long emAndamento = 0L;
        Long concluido = 0L;
        Long cancelado = 0L;
        for(StatusEtapa statusEtapa : statusEtapaList){
            total = statusEtapaProjetoRepository.countByStatusEtapaId(statusEtapa.getId());
            if(statusEtapa.getId() == 1L){
                naoIniciado = total;
            } else if(statusEtapa.getId() == 2L){
                emAndamento = total;
            } else if(statusEtapa.getId() == 3L){
                concluido = total;
            } else if(statusEtapa.getId() == 4L){
                cancelado = total;
            }
        }
        total = statusEtapaProjetoRepository.count();
        ContagemEtapaDTO contagemEtapaDTO = new ContagemEtapaDTO(total, naoIniciado, emAndamento, concluido, cancelado);
        return contagemEtapaDTO;
    }
    @Transactional
    public StatusEtapaProjetoDetalhamentoDTO associaTecnico(AssociaTecnicoEtapaDTO associaTecnicoEtapaDTO){
        StatusEtapaProjeto statusEtapaProjeto = statusEtapaProjetoRepository.findById(associaTecnicoEtapaDTO.statusEtapaProjetoId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado uma etapa com o id " + associaTecnicoEtapaDTO.statusEtapaProjetoId()));
        statusEtapaProjeto.setUsuario(usuarioRepository.findById(associaTecnicoEtapaDTO.usuarioId())
                .orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um técnico com o id " + associaTecnicoEtapaDTO.usuarioId())));
        return new StatusEtapaProjetoDetalhamentoDTO(statusEtapaProjeto);
    }

    @Transactional
    public StatusEtapaProjetoDetalhamentoDTO iniciarEtapa(Long id){
        StatusEtapaProjeto statusEtapaProjeto = statusEtapaProjetoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        statusEtapaProjeto.setStatusEtapa(statusEtapaRepository.findById(2L).orElseThrow(EntityNotFoundException::new));
        statusEtapaProjeto.setDatainicio(new Date());
        statusEtapaProjeto.setCadastro(statusEtapaProjeto);
        return new StatusEtapaProjetoDetalhamentoDTO(statusEtapaProjeto);
    }

    public List<StatusEtapaProjetoDetalhamentoDTO> listarPorTecnico() {
        return statusEtapaProjetoRepository.findAllByUsuarioId(usuarioService.getUsuarioPorId(null).id()).stream().map(StatusEtapaProjetoDetalhamentoDTO::new).toList();
    }*/



}
