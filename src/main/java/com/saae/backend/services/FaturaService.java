package com.saae.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.Fatura;
import com.saae.backend.repositories.FaturaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FaturaService {

    @Autowired
    private FaturaRepository faturaRepository;

    // Listar todas as faturas
    public List<Fatura> listarFaturas() {
        return faturaRepository.findAll();
    }

    // Listar as faturas de um imóvel
    public List<Fatura> listarFaturasPorImovel(Long imovelId) {
        return faturaRepository.findByImovelId(imovelId);
    }

    // Obter uma fatura por ID
    public Optional<Fatura> obterFaturaPorId(Long id) {
        return faturaRepository.findById(id);
    }

    // Criar uma nova fatura
    public Fatura criarFatura(Fatura fatura) {
        return faturaRepository.save(fatura);
    }

    // Atualizar uma fatura existente
    public Fatura atualizarFatura(Long id, Fatura fatura) {
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
