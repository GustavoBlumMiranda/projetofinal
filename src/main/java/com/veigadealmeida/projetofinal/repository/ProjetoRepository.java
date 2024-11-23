package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    @Query("SELECT p FROM projeto p " +
            "JOIN p.usuarios u " +
            "WHERE u.id = :usuarioId")
    List<Projeto> findProjetosByUsuarioId(@Param("usuarioId") Long usuarioId);

    Page<Projeto> findAllByUsuariosIsNull(Pageable paginacao);
}
