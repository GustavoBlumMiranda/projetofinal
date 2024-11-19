package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Pergunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    Page<Pergunta> findAll(Pageable paginacao);

}
