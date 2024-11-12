package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Etapa;
import com.veigadealmeida.projetofinal.domain.PerguntaEtapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerguntaEtapaRepository extends JpaRepository<PerguntaEtapa, Long> {
    Optional<PerguntaEtapa> findById(Long id);

    List<PerguntaEtapa> findAllByEtapa(Etapa etapa);

}
