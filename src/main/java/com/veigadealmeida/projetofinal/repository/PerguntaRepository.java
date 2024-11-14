package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Pergunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    //Optional<Pergunta> findById(Long id);
    Page<Pergunta> findAll(Pageable paginacao);

}
