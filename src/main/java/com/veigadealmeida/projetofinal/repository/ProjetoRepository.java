package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    Long countByStatusProjetoId(Long id);
}
