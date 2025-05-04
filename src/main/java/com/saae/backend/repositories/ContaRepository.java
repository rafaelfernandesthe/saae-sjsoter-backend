package com.saae.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.saae.backend.entities.Conta;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByImovelId(Long imovelId);

    Optional<Conta> findById(Long id);
}
