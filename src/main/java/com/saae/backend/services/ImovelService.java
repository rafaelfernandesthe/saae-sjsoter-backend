package com.saae.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.Imovel;
import com.saae.backend.repositories.ImovelRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public List<Imovel> listarImoveis() {
        return imovelRepository.findAll();
    }

    public Optional<Imovel> obterImovelPorId(Long id) {
        return imovelRepository.findById(id);
    }

    public Imovel criarImovel(Imovel imovel) {
        return imovelRepository.save(imovel);
    }

    public Imovel atualizarImovel(Long id, Imovel imovel) {
        if (imovelRepository.existsById(id)) {
            imovel.setId(id);
            return imovelRepository.save(imovel);
        }
        return null;
    }

    public boolean deletarImovel(Long id) {
        if (imovelRepository.existsById(id)) {
            imovelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
