package com.saae.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saae.backend.entities.TaxaImposto;

@Repository
public interface TaxaImpostoRepository extends JpaRepository<TaxaImposto, Long> {

}
