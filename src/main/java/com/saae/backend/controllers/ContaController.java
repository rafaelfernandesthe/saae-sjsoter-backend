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
    private ContaService contaService;

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listar();
    }

    // Listar as contas de um imóvel específico
    @GetMapping("/imovel/{imovelId}")
    public List<Conta> listarContasPorImovel(@PathVariable Long imovelId) {
        return contaService.listarPorImovel(imovelId);
    }

    // Obter uma conta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conta> obterConta(@PathVariable Long id) {
        Optional<Conta> conta = contaService.obterPorId(id);
        return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar uma nova conta
    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
        Conta novaConta = contaService.criar(conta);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    // Atualizar uma conta existente
    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta conta) {
        Conta contaAtualizada = contaService.atualizar(id, conta);
        return contaAtualizada != null ? ResponseEntity.ok(contaAtualizada) : ResponseEntity.notFound().build();
    }

    // Deletar uma conta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        return contaService.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
