package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Etapa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Long> {
    Page<Etapa> findAll(Pageable paginacao);
}
