package com.assessment.service_cliente.service;

import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.model.ProdutoDTO;
import com.assessment.service_cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final RestTemplate restTemplate;

    @Value("${SERVICE_API_URL:http://service-api:8081}")
    private String serviceApiUrl;

    public ClienteService(ClienteRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    // 🔹 NOVO: Consome o service-api e retorna lista de produtos
    public List<ProdutoDTO> listarProdutosDoServiceApi() {
        try {
            String url = serviceApiUrl + "/produtos";
            ProdutoDTO[] produtos = restTemplate.getForObject(url, ProdutoDTO[].class);
            return Arrays.asList(produtos);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produtos do service-api: " + e.getMessage(), e);
        }
    }
}
