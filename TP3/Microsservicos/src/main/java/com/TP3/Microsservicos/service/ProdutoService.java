package com.TP3.Microsservicos.service;


import com.TP3.Microsservicos.model.Produto;
import com.TP3.Microsservicos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todos os produtos de forma reativa.
     * Como o Spring Data JDBC é bloqueante, usamos Flux.defer()
     * para evitar bloqueio na thread principal.
     */
    public Flux<Produto> listarTodos() {
        return Flux.defer(() -> Flux.fromIterable(repository.findAll()));
    }

    /**
     * Salva um produto de forma reativa.
     * Mono.fromCallable() permite executar uma operação bloqueante
     * dentro de um fluxo reativo.
     */
    public Mono<Produto> salvar(Produto produto) {
        return Mono.fromCallable(() -> repository.save(produto));
    }

    /**
     * Busca um produto por ID.
     */
    public Mono<Produto> buscarPorId(Long id) {
        return Mono.fromCallable(() -> repository.findById(id).orElse(null));
    }

    /**
     * Remove um produto pelo ID.
     */
    public Mono<Void> deletar(Long id) {
        return Mono.fromRunnable(() -> repository.deleteById(id));
    }
}
