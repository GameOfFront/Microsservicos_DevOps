package com.assessment.service_api.repository;

import com.assessment.service_api.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")

@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void deveSalvarEBuscarProduto() {
        Produto produto = Produto.builder()
                .nome("Cadeira Gamer")
                .preco(1500.0)
                .build();

        produtoRepository.save(produto);

        List<Produto> todos = produtoRepository.findAll();

        assertEquals(1, todos.size());
        assertEquals("Cadeira Gamer", todos.get(0).getNome());
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = produtoRepository.save(Produto.builder()
                .nome("Teclado")
                .preco(200.0)
                .build());

        Optional<Produto> encontrado = produtoRepository.findById(produto.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Teclado", encontrado.get().getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistirProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        assertTrue(produtos.isEmpty());
    }
}
