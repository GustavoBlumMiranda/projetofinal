package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.EtapaEmUso;
import com.veigadealmeida.projetofinal.domain.RespostasEtapaEmUso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostasEtapaEmUsoRepository extends JpaRepository<RespostasEtapaEmUso, Long> {
    Integer countRespostasEtapaEmUsoByEtapaEmUso(EtapaEmUso etapaEmUso);
}
