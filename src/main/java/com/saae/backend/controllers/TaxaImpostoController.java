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

import com.saae.backend.entities.TaxaImposto;
import com.saae.backend.services.TaxaImpostoService;

@RestController
@RequestMapping("/taxasImpostos")
public class TaxaImpostoController {

	@Autowired
	private TaxaImpostoService taxaImpostoService;

	@GetMapping
	public ResponseEntity<List<TaxaImposto>> listar() {
		return ResponseEntity.ok(taxaImpostoService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaxaImposto> obterTaxaImposto(@PathVariable Long id) {
		Optional<TaxaImposto> taxaImposto = taxaImpostoService.obterPorId(id);
		return taxaImposto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<TaxaImposto> criarTaxaImposto(@RequestBody TaxaImposto taxaImposto) {
		TaxaImposto novoTaxaImposto = taxaImpostoService.criar(taxaImposto);
		return new ResponseEntity<>(novoTaxaImposto, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaxaImposto> atualizarTaxaImposto(@PathVariable Long id,
			@RequestBody TaxaImposto taxaImposto) {
		TaxaImposto taxaImpostoAtualizado = taxaImpostoService.atualizar(id, taxaImposto);
		return taxaImpostoAtualizado != null ? ResponseEntity.ok(taxaImpostoAtualizado)
				: ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarTaxaImposto(@PathVariable Long id) {
		return taxaImpostoService.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
