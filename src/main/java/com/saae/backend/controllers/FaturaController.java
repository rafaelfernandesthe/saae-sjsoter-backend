package com.saae.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saae.backend.entities.Fatura;
import com.saae.backend.services.FaturaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/faturas")
public class FaturaController {

    @Autowired
    private FaturaService faturaService;

    // Listar todas as faturas
    @GetMapping
    public List<Fatura> listarFaturas() {
        return faturaService.listarFaturas();
    }

    // Listar as faturas de um imóvel específico
    @GetMapping("/imovel/{imovelId}")
    public List<Fatura> listarFaturasPorImovel(@PathVariable Long imovelId) {
        return faturaService.listarFaturasPorImovel(imovelId);
    }

    // Obter uma fatura por ID
    @GetMapping("/{id}")
    public ResponseEntity<Fatura> obterFatura(@PathVariable Long id) {
        Optional<Fatura> fatura = faturaService.obterFaturaPorId(id);
        return fatura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar uma nova fatura
    @PostMapping
    public ResponseEntity<Fatura> criarFatura(@RequestBody Fatura fatura) {
        Fatura novaFatura = faturaService.criarFatura(fatura);
        return new ResponseEntity<>(novaFatura, HttpStatus.CREATED);
    }

    // Atualizar uma fatura existente
    @PutMapping("/{id}")
    public ResponseEntity<Fatura> atualizarFatura(@PathVariable Long id, @RequestBody Fatura fatura) {
        Fatura faturaAtualizada = faturaService.atualizarFatura(id, fatura);
        return faturaAtualizada != null ? ResponseEntity.ok(faturaAtualizada) : ResponseEntity.notFound().build();
    }

    // Deletar uma fatura por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFatura(@PathVariable Long id) {
        return faturaService.deletarFatura(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
