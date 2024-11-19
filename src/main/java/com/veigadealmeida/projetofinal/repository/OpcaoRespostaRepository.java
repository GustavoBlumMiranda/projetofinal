package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.OpcaoResposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OpcaoRespostaRepository extends JpaRepository<OpcaoResposta, Long>{
}
