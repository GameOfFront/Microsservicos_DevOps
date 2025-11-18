package com.TP3.Microsservicos.controller;

import com.TP3.Microsservicos.model.Produto;
import com.TP3.Microsservicos.service.ProdutoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    /**
     * Retorna todos os produtos de forma reativa.
     * Exemplo: GET /produtos
     */
    @GetMapping
    public Flux<Produto> listarTodos() {
        return service.listarTodos();
    }

    /**
     * Busca um produto específico por ID.
     * Exemplo: GET /produtos/1
     */
    @GetMapping("/{id}")
    public Mono<Produto> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    /**
     * Salva um novo produto.
     * Exemplo: POST /produtos
     * Body JSON: { "nome": "Mouse Gamer", "preco": 250.0 }
     */
    @PostMapping
    public Mono<Produto> salvar(@RequestBody Produto produto) {
        return service.salvar(produto);
    }

    /**
     * Remove um produto por ID.
     * Exemplo: DELETE /produtos/1
     */
    @DeleteMapping("/{id}")
    public Mono<Void> deletar(@PathVariable Long id) {
        return service.deletar(id);
    }
}
