package com.saae.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.Pagamento;
import com.saae.backend.repositories.PagamentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    // Listar todos os pagamentos
    public List<Pagamento> listar() {
        return pagamentoRepository.findAll();
    }

    // Obter pagamento por ID
    public Optional<Pagamento> obterPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    // Criar um novo pagamento
    public Pagamento criar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    // Atualizar um pagamento existente
    public Pagamento atualizar(Long id, Pagamento pagamento) {
        if (pagamentoRepository.existsById(id)) {
            pagamento.setId(id);
            return pagamentoRepository.save(pagamento);
        }
        return null;
    }

    // Deletar pagamento por ID
    public boolean deletar(Long id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
