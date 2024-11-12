package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.TemplateProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateProjetoRepository extends JpaRepository<TemplateProjeto, Long> {
    TemplateProjeto findByIdAndAtivo(Long id, boolean ativo);
}
