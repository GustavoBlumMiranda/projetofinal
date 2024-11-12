package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.AlterarSenhaToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenResetSenhaRepository extends JpaRepository<AlterarSenhaToken, Long> {
        AlterarSenhaToken findByToken(String token);
}
