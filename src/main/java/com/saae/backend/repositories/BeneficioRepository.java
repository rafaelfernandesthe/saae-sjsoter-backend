package com.saae.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saae.backend.entities.Beneficio;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

}
