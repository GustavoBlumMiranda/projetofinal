package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.EtapaTemplateProjeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtapaTemplateProjetoRepository extends JpaRepository<EtapaTemplateProjeto, Long> {
    Page<EtapaTemplateProjeto> findAll(Pageable paginacao);
    Optional<EtapaTemplateProjeto> findById(Long id);
    List<EtapaTemplateProjeto> findAllByTemplateProjetoId(Long idTemplateProjeto);
}
