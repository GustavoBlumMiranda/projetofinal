package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespostasProjetoRepository extends JpaRepository<RespostasProjeto, Long>{
    Page<RespostasProjeto> findAll(Pageable paginacao);

    Page<RespostasProjeto> findAllByUsuario(Pageable paginacao, Usuario usuario);
    Optional<RespostasProjeto> findByPerguntaAndEtapaAndProjeto(Pergunta pergunta, Etapa etapa, Projeto projeto);

    Long countByAprovada(Boolean aprovada);

    List<RespostasProjeto> findByEtapaAndProjeto(Etapa etapa, Projeto projeto);

}
