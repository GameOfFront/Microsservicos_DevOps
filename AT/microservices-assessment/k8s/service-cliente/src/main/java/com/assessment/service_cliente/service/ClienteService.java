package com.assessment.service_cliente.service;

import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.model.ProdutoDTO;
import com.assessment.service_cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final WebClient webClient;

    @Value("${SERVICE_API_URL:http://service-api:8081}")
    private String serviceApiUrl;

    public ClienteService(ClienteRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public Flux<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Mono<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Mono<Cliente> salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Mono<Void> deletar(Long id) {
        return repository.deleteById(id);
    }

    // 🔹 Consome o service-api reativamente
    public Flux<ProdutoDTO> listarProdutosDoServiceApi() {
        String url = serviceApiUrl + "/produtos";
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(ProdutoDTO.class);
    }
}
