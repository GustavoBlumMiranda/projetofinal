package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Cabecalho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CabecalhoRepository extends JpaRepository<Cabecalho, Long> {

    @Override
    Page<Cabecalho> findAll(Pageable paginacao);
    Optional<Cabecalho> findById(Long id);
}
