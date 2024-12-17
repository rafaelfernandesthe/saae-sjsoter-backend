package com.saae.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saae.backend.entities.Imovel;
import com.saae.backend.services.ImovelService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/imoveis")
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @GetMapping
    public List<Imovel> listarImoveis() {
        return imovelService.listarImoveis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imovel> obterImovel(@PathVariable Long id) {
        Optional<Imovel> imovel = imovelService.obterImovelPorId(id);
        return imovel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Imovel> criarImovel(@RequestBody Imovel imovel) {
        Imovel novoImovel = imovelService.criarImovel(imovel);
        return new ResponseEntity<>(novoImovel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imovel> atualizarImovel(@PathVariable Long id, @RequestBody Imovel imovel) {
        Imovel imovelAtualizado = imovelService.atualizarImovel(id, imovel);
        return imovelAtualizado != null ? ResponseEntity.ok(imovelAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarImovel(@PathVariable Long id) {
        return imovelService.deletarImovel(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
