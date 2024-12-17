package com.saae.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.saae.backend.entities.Fatura;

import java.util.List;
import java.util.Optional;

public interface FaturaRepository extends JpaRepository<Fatura, Long> {

    // Listar todas as faturas de um imóvel
    List<Fatura> findByImovelId(Long imovelId);

    // Obter uma fatura por ID
    Optional<Fatura> findById(Long id);
}
