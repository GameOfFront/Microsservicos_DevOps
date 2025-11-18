package com.TP3.Microsservicos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Cliente HTTP reativo para consultar informações externas.
 * Usa o WebClient do Spring WebFlux.
 */
@Component
public class PrecoClient {

    private final WebClient webClient;

    public PrecoClient(WebClient.Builder builder) {
        // URL base de exemplo (API pública de produtos falsos)
        this.webClient = builder.baseUrl("https://fakestoreapi.com/products").build();
    }

    /**
     * Obtém o preço de um produto simulando integração com uma API externa.
     * Retorna um Mono<Double> de forma reativa.
     */
    public Mono<Double> obterPreco(Long idProduto) {
        return webClient
                .get()
                .uri("/{id}", idProduto)
                .retrieve()
                .bodyToMono(String.class)
                // Simulando cálculo de preço, pois a API de exemplo retorna JSON complexo
                .map(response -> Math.random() * 100);
    }
}
