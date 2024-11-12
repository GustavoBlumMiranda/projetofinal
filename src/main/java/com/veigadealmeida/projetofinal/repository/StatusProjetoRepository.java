package com.veigadealmeida.projetofinal.repository;


import com.veigadealmeida.projetofinal.domain.StatusProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusProjetoRepository extends JpaRepository<StatusProjeto, Long> {
}
