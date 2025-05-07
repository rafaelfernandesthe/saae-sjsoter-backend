package com.saae.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.TaxaImposto;
import com.saae.backend.entities.enums.StatusTaxaImposto;
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
		taxaImposto.setStatus(StatusTaxaImposto.ATIVO);
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
		
		//nao pode apagar a primeira taxa de imposto, deve existir pelo menos uma
		if(Long.valueOf(1).equals(id)) {
			return false;
		}
		
		if (taxaImpostoRepository.existsById(id)) {
			taxaImpostoRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
