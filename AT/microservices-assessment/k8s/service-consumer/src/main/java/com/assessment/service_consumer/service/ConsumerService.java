package com.assessment.service_consumer.service;

import com.assessment.service_consumer.model.Cliente;
import com.assessment.service_consumer.model.Produto;
import com.assessment.service_consumer.model.ResumoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ConsumerService {

    private final RestTemplate restTemplate;

    @Value("${service.api.url}")
    private String serviceApiUrl;

    @Value("${service.cliente.url}")
    private String serviceClienteUrl;

    public ConsumerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ✅ PRODUTOS
    public List<Produto> listarProdutos() {
        Produto[] produtos = restTemplate.getForObject(serviceApiUrl + "/produtos", Produto[].class);
        return Arrays.asList(produtos);
    }

    public Produto criarProduto(Produto produto) {
        return restTemplate.postForObject(serviceApiUrl + "/produtos", produto, Produto.class);
    }

    // ✅ CLIENTES
    public List<Cliente> listarClientes() {
        Cliente[] clientes = restTemplate.getForObject(serviceClienteUrl + "/clientes", Cliente[].class);
        return Arrays.asList(clientes);
    }

    public Cliente criarCliente(Cliente cliente) {
        return restTemplate.postForObject(serviceClienteUrl + "/clientes", cliente, Cliente.class);
    }

    // ✅ AGREGAÇÃO
    public ResumoResponse resumoGeral() {
        List<Produto> produtos = listarProdutos();
        List<Cliente> clientes = listarClientes();

        return new ResumoResponse(
                produtos.size(),
                clientes.size(),
                produtos,
                clientes
        );
    }
}
