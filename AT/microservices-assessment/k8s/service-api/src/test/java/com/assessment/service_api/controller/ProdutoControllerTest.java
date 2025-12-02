package com.assessment.service_api.controller;

import com.assessment.service_api.model.Produto;
import com.assessment.service_api.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    void deveListarTodosOsProdutos() {
        List<Produto> produtosMock = Arrays.asList(
                new Produto(1L, "Notebook", 3500.0),
                new Produto(2L, "Mouse", 150.0)
        );

        when(produtoRepository.findAll()).thenReturn(produtosMock);

        List<Produto> resultado = produtoController.listar();

        assertEquals(2, resultado.size());
        assertEquals("Notebook", resultado.get(0).getNome());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        Produto novo = new Produto(null, "Teclado", 200.0);
        Produto salvo = new Produto(1L, "Teclado", 200.0);

        when(produtoRepository.save(novo)).thenReturn(salvo);

        Produto resultado = produtoController.salvar(novo);

        assertNotNull(resultado.getId());
        assertEquals("Teclado", resultado.getNome());
        verify(produtoRepository, times(1)).save(novo);
    }
}
