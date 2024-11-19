package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.EtapaProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtapaProjetoRepository extends JpaRepository<EtapaProjeto, Long> {

}
