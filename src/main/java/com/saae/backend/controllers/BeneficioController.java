package com.saae.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saae.backend.entities.Beneficio;
import com.saae.backend.services.BeneficioService;

@RestController
@RequestMapping("/beneficios")
public class BeneficioController {

	@Autowired
	private BeneficioService beneficioService;

	@GetMapping
	public ResponseEntity<List<Beneficio>> listar() {
		return ResponseEntity.ok(beneficioService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Beneficio> obterBeneficio(@PathVariable Long id) {
		Optional<Beneficio> beneficio = beneficioService.obterPorId(id);
		return beneficio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Beneficio> criarBeneficio(@RequestBody Beneficio beneficio) {
		Beneficio novoBeneficio = beneficioService.criar(beneficio);
		return new ResponseEntity<>(novoBeneficio, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Beneficio> atualizarBeneficio(@PathVariable Long id, @RequestBody Beneficio beneficio) {
		Beneficio beneficioAtualizado = beneficioService.atualizar(id, beneficio);
		return beneficioAtualizado != null ? ResponseEntity.ok(beneficioAtualizado) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarBeneficio(@PathVariable Long id) {
		return beneficioService.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
