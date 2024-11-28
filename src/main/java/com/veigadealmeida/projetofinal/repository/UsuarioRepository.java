package com.veigadealmeida.projetofinal.repository;

import com.veigadealmeida.projetofinal.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);

    Page<Usuario> findAll(Pageable paginacao);

    List<Usuario> findAllByLogin(String username);
  
    List<Usuario> findAllByEmail(String email);
  
    List<Usuario> findAllByCodUsuario(String codUsuario);
  
    List<Usuario> findAllByCpf(String cpf);

    @Query("SELECT u FROM Usuario u WHERE u.id NOT IN " +
            "(SELECT p.id FROM projeto pr JOIN pr.usuarios p WHERE pr.id = :projetoId)")
    Page<Usuario> findUsuariosDisponiveisParaAssociacao(Pageable pageable, @Param("projetoId") Long projetoId);
}

}