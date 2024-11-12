package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.OpcaoResposta;
import com.veigadealmeida.projetofinal.domain.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcaoRespostaRepository extends JpaRepository<OpcaoResposta, Long>{
    List<OpcaoResposta> findByPergunta(Pergunta pergunta);
}
