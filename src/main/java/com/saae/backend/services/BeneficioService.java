package com.saae.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.Beneficio;
import com.saae.backend.repositories.BeneficioRepository;

@Service
public class BeneficioService {

	@Autowired
	private BeneficioRepository beneficioRepository;

	public List<Beneficio> listar() {
		return beneficioRepository.findAll();
	}

	public Optional<Beneficio> obterPorId(Long id) {
		return beneficioRepository.findById(id);
	}

	public Beneficio criar(Beneficio beneficio) {
		return beneficioRepository.save(beneficio);
	}

	public Beneficio atualizar(Long id, Beneficio beneficio) {
		if (beneficioRepository.existsById(id)) {
			beneficio.setId(id);
			return beneficioRepository.save(beneficio);
		}
		return null;
	}

	public boolean deletar(Long id) {
		if (beneficioRepository.existsById(id)) {
			beneficioRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
