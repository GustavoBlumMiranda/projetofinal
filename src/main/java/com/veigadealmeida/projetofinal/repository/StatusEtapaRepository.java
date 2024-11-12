package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.StatusEtapa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
@Service
public interface StatusEtapaRepository extends JpaRepository<StatusEtapa, Long>{
    Page<StatusEtapa> findAll(Pageable paginacao);
}
