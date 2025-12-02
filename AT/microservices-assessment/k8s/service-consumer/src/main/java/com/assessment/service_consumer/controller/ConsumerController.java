package com.assessment.service_consumer.controller;

import com.assessment.service_consumer.model.Cliente;
import com.assessment.service_consumer.model.Produto;
import com.assessment.service_consumer.model.ResumoResponse;
import com.assessment.service_consumer.service.ConsumerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    // ✅ PRODUTOS
    @GetMapping("/produtos")
    public List<Produto> listarProdutos() {
        return consumerService.listarProdutos();
    }

    @PostMapping("/produtos")
    public Produto criarProduto(@RequestBody Produto produto) {
        return consumerService.criarProduto(produto);
    }

    // ✅ CLIENTES
    @GetMapping("/clientes")
    public List<Cliente> listarClientes() {
        return consumerService.listarClientes();
    }

    @PostMapping("/clientes")
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return consumerService.criarCliente(cliente);
    }

    // ✅ RESUMO (agregação)
    @GetMapping("/resumo")
    public ResumoResponse resumoGeral() {
        return consumerService.resumoGeral();
    }
}
