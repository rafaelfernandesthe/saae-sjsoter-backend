package com.saae.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.TaxaImposto;
import com.saae.backend.repositories.TaxaImpostoRepository;

@Service
public class TaxaImpostoService {

	@Autowired
	private TaxaImpostoRepository taxaImpostoRepository;

	public List<TaxaImposto> listar() {
		return taxaImpostoRepository.findAll();
	}

	public Optional<TaxaImposto> obterPorId(Long id) {
		return taxaImpostoRepository.findById(id);
	}

	public TaxaImposto criar(TaxaImposto taxaImposto) {
		return taxaImpostoRepository.save(taxaImposto);
	}

	public TaxaImposto atualizar(Long id, TaxaImposto taxaImposto) {
		if (taxaImpostoRepository.existsById(id)) {
			taxaImposto.setId(id);
			return taxaImpostoRepository.save(taxaImposto);
		}
		return null;
	}

	public boolean deletar(Long id) {
		if (taxaImpostoRepository.existsById(id)) {
			taxaImpostoRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
