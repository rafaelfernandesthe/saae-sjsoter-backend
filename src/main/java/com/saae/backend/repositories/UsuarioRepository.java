package com.saae.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.saae.backend.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNome(String nome);

    @Query("SELECT u FROM Usuario u WHERE u.email = ?1 AND u.ativo = true")
    Optional<Usuario> findByEmailAtivo(String email);

    long count();
}
