package com.saae.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.Conta;
import com.saae.backend.repositories.ContaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository faturaRepository;

    // Listar todas as faturas
    public List<Conta> listarFaturas() {
        return faturaRepository.findAll();
    }

    // Listar as faturas de um imóvel
    public List<Conta> listarFaturasPorImovel(Long imovelId) {
        return faturaRepository.findByImovelId(imovelId);
    }

    // Obter uma fatura por ID
    public Optional<Conta> obterFaturaPorId(Long id) {
        return faturaRepository.findById(id);
    }

    // Criar uma nova fatura
    public Conta criarFatura(Conta fatura) {
        return faturaRepository.save(fatura);
    }

    // Atualizar uma fatura existente
    public Conta atualizarFatura(Long id, Conta fatura) {
        if (faturaRepository.existsById(id)) {
            fatura.setId(id);
            return faturaRepository.save(fatura);
        }
        return null;
    }

    // Deletar uma fatura
    public boolean deletarFatura(Long id) {
        if (faturaRepository.existsById(id)) {
            faturaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
