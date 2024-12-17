package com.saae.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saae.backend.entities.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    // Métodos personalizados de consulta, se necessário
    List<Pagamento> findByFaturaId(Long faturaId);

    // Verificar pagamentos realizados por um método de pagamento
    List<Pagamento> findByMetodoPagamento(String metodoPagamento);
}
