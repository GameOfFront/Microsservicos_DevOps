package com.springboot_api.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private List<String> produtos = new ArrayList<>(Arrays.asList("Notebook", "Mouse", "Teclado"));

    @GetMapping
    public List<String> listarProdutos() {
        return produtos;
    }

    @GetMapping("/hello")
    public String hello() {
        return "API Spring Boot rodando com sucesso no Docker/Kubernetes!";
    }

    @PostMapping
    public String adicionarProduto(@RequestBody String produto) {
        produtos.add(produto);
        return "Produto adicionado: " + produto;
    }

    @DeleteMapping("/{id}")
    public String removerProduto(@PathVariable int id) {
        if (id >= 0 && id < produtos.size()) {
            String removido = produtos.remove(id);
            return "Produto removido: " + removido;
        }
        return "ID inválido!";
    }

    @GetMapping("/count")
    public String contarProdutos() {
        return "Total de produtos: " + produtos.size();
    }
}
