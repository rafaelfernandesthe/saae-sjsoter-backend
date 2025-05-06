package com.saae.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saae.backend.entities.Pagamento;
import com.saae.backend.services.PagamentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    // Listar todos os pagamentos
    @GetMapping
    public List<Pagamento> listarPagamentos() {
        return pagamentoService.listar();
    }

    // Obter pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> obterPagamento(@PathVariable Long id) {
        Optional<Pagamento> pagamento = pagamentoService.obterPorId(id);
        return pagamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar um novo pagamento
    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@RequestBody Pagamento pagamento) {
        Pagamento novoPagamento = pagamentoService.criar(pagamento);
        return new ResponseEntity<>(novoPagamento, HttpStatus.CREATED);
    }

    // Atualizar um pagamento existente
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Long id, @RequestBody Pagamento pagamento) {
        Pagamento pagamentoAtualizado = pagamentoService.atualizar(id, pagamento);
        return pagamentoAtualizado != null ? ResponseEntity.ok(pagamentoAtualizado) : ResponseEntity.notFound().build();
    }

    // Deletar um pagamento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id) {
        return pagamentoService.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
