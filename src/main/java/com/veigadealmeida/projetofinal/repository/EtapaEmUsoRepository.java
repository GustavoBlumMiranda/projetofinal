package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.EtapaEmUso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapaEmUsoRepository extends JpaRepository<EtapaEmUso, Long> {
    @Query("SELECT e FROM EtapaEmUso e " +
            "JOIN e.etapaProjeto ep " +
            "JOIN ep.projeto p " +
            "WHERE e.usuario.id = :usuarioId AND p.id = :projetoId")
    List<EtapaEmUso> findByUsuarioIdAndProjetoId(@Param("usuarioId") Long usuarioId, @Param("projetoId") Long projetoId);
}

