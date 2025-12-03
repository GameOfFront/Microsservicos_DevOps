package com.assessment.service_cliente.controller;

import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.model.ProdutoDTO;
import com.assessment.service_cliente.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Cliente> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Cliente>> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Cliente> adicionar(@RequestBody Cliente cliente) {
        return service.salvar(cliente);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletar(@PathVariable Long id) {
        return service.deletar(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    // 🔹 Chamada reativa ao service-api
    @GetMapping("/produtos")
    public Flux<ProdutoDTO> listarProdutosDoServiceApi() {
        return service.listarProdutosDoServiceApi();
    }
}
