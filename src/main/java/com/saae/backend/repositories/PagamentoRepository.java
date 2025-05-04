package com.saae.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saae.backend.entities.Pagamento;
import com.saae.backend.entities.enums.MetodoPagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByContaId(Long contaId);

    List<Pagamento> findByMetodo(MetodoPagamento metodoPagamento);
}
