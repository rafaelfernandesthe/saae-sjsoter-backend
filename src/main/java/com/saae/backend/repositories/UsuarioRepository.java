package com.saae.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.saae.backend.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuário pelo email (útil para autenticação)
    Optional<Usuario> findByEmail(String email);

    // Buscar usuário pelo nome (exemplo de busca personalizada)
    Optional<Usuario> findByNome(String nome);

    // Exemplo de consulta personalizada com @Query
    @Query("SELECT u FROM Usuario u WHERE u.email = ?1 AND u.ativo = true")
    Optional<Usuario> findByEmailAtivo(String email);

    // Método adicional para contar quantos usuários existem
    long count();
}
