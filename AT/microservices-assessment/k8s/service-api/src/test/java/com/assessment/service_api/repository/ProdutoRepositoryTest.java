package com.assessment.service_api.repository;

import com.assessment.service_api.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ActiveProfiles("test")
@DataR2dbcTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void deveSalvarEBuscarProdutoReativamente() {
        Produto produto = new Produto(null, "Cadeira Gamer", 1500.0);

        Mono<Produto> fluxo = produtoRepository.save(produto)
                .flatMap(saved -> produtoRepository.findById(saved.getId()));

        StepVerifier.create(fluxo)
                .expectNextMatches(p -> p.getNome().equals("Cadeira Gamer"))
                .verifyComplete();
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrarProduto() {
        StepVerifier.create(produtoRepository.findById(999L))
                .verifyComplete();
    }
}
