package com.saae.backend.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saae.backend.entities.Imovel;
import com.saae.backend.services.ImovelService;

@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @GetMapping
	public ResponseEntity<Page<Imovel>> listarImoveis(Pageable pageable, @RequestParam(required = false) String tipo,
		    @RequestParam(required = false) String rua,
		    @RequestParam(required = false) String numero,
		    @RequestParam(required = false) String bairro,
		    @RequestParam(required = false) String proprietario,
		    @RequestParam(required = false) String cpfCnpj) {
	    Page<Imovel> imoveis = imovelService.listarImoveis(pageable, tipo, rua, numero, bairro, proprietario, cpfCnpj);
	    return ResponseEntity.ok(imoveis);
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
