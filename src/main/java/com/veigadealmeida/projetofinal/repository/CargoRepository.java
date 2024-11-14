package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Grupo, Long> {

    Optional<Grupo> findCargoById(Long id);
}
