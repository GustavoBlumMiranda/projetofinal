package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Optional<Cargo> findCargoById(Long id);
}
