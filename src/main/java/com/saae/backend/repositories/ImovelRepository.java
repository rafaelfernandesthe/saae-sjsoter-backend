package com.saae.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.saae.backend.entities.Imovel;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
    // Métodos de consulta personalizados podem ser adicionados aqui
}
