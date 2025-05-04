package com.saae.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saae.backend.entities.Conta;
import com.saae.backend.services.ContaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService faturaService;

    @GetMapping
    public List<Conta> listarFaturas() {
        return faturaService.listarFaturas();
    }

    // Listar as faturas de um imóvel específico
    @GetMapping("/imovel/{imovelId}")
    public List<Conta> listarFaturasPorImovel(@PathVariable Long imovelId) {
        return faturaService.listarFaturasPorImovel(imovelId);
    }

    // Obter uma fatura por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conta> obterFatura(@PathVariable Long id) {
        Optional<Conta> fatura = faturaService.obterFaturaPorId(id);
        return fatura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar uma nova fatura
    @PostMapping
    public ResponseEntity<Conta> criarFatura(@RequestBody Conta fatura) {
        Conta novaFatura = faturaService.criarFatura(fatura);
        return new ResponseEntity<>(novaFatura, HttpStatus.CREATED);
    }

    // Atualizar uma fatura existente
    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizarFatura(@PathVariable Long id, @RequestBody Conta fatura) {
        Conta faturaAtualizada = faturaService.atualizarFatura(id, fatura);
        return faturaAtualizada != null ? ResponseEntity.ok(faturaAtualizada) : ResponseEntity.notFound().build();
    }

    // Deletar uma fatura por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFatura(@PathVariable Long id) {
        return faturaService.deletarFatura(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
