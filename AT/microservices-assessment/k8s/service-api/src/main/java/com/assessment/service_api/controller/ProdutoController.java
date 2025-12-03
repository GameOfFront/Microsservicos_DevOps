package com.assessment.service_api.controller;

import com.assessment.service_api.model.Produto;
import com.assessment.service_api.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Produto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Mono<Produto> salvar(@RequestBody Produto produto) {
        return repository.save(produto);
    }
}
