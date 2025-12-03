package com.assessment.service_api.controller;

import com.assessment.service_api.model.Produto;
import com.assessment.service_api.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class ProdutoControllerTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodosOsProdutosReativamente() {
        Produto p1 = new Produto(1L, "Notebook", 3500.0);
        Produto p2 = new Produto(2L, "Mouse", 150.0);

        when(produtoRepository.findAll()).thenReturn(Flux.just(p1, p2));

        StepVerifier.create(produtoController.listar())
                .expectNext(p1)
                .expectNext(p2)
                .verifyComplete();

        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        Produto novo = new Produto(null, "Teclado", 200.0);
        Produto salvo = new Produto(1L, "Teclado", 200.0);

        when(produtoRepository.save(novo)).thenReturn(Mono.just(salvo));

        StepVerifier.create(produtoController.salvar(novo))
                .expectNextMatches(p -> p.getId() == 1L && p.getNome().equals("Teclado"))
                .verifyComplete();

        verify(produtoRepository, times(1)).save(novo);
    }
}
