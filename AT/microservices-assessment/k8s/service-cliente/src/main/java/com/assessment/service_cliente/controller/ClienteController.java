package com.assessment.service_cliente.controller;

import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.model.ProdutoDTO;
import com.assessment.service_cliente.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente adicionar(@RequestBody Cliente cliente) {
        return service.salvar(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 NOVO: endpoint que consome o service-api
    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosDoServiceApi() {
        List<ProdutoDTO> produtos = service.listarProdutosDoServiceApi();
        return ResponseEntity.ok(produtos);
    }
}
