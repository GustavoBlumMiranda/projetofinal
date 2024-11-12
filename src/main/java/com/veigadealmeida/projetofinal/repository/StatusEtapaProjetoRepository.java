package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Etapa;
import com.veigadealmeida.projetofinal.domain.Projeto;
import com.veigadealmeida.projetofinal.domain.StatusEtapa;
import com.veigadealmeida.projetofinal.domain.StatusEtapaProjeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusEtapaProjetoRepository extends JpaRepository<StatusEtapaProjeto, Long>{
    Page<StatusEtapaProjeto> findAll(Pageable paginacao);

    List<StatusEtapaProjeto> findAllByProjetoId(Long idProjeto);

    Optional<StatusEtapaProjeto> findByEtapaAndProjeto(Etapa etapa, Projeto projeto);

    Integer countAllByProjetoAndStatusEtapa(Projeto projeto, StatusEtapa statusEtapa);

    Integer countAllByProjeto(Projeto projeto);

    Long countByStatusEtapaId(Long id);

    List<StatusEtapaProjeto> findAllByUsuarioId(Long idUsuario);
}
