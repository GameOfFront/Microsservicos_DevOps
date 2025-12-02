package com.assessment.service_cliente;

import com.assessment.service_cliente.controller.ClienteController;
import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.model.ProdutoDTO;
import com.assessment.service_cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService service;

    @InjectMocks
    private ClienteController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarClientes() {
        when(service.listarTodos()).thenReturn(List.of(new Cliente()));

        List<Cliente> resultado = controller.listar();

        assertEquals(1, resultado.size());
        verify(service, times(1)).listarTodos();
    }

    @Test
    void deveBuscarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("Paulo");

        when(service.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        ResponseEntity<Cliente> resposta = controller.buscarPorId(1L);

        assertEquals(200, resposta.getStatusCode().value());

        assertEquals("Paulo", resposta.getBody().getNome());
    }

    @Test
    void deveRetornar404QuandoNaoEncontrarCliente() {
        when(service.buscarPorId(2L)).thenReturn(Optional.empty());

        ResponseEntity<Cliente> resposta = controller.buscarPorId(2L);

        assertEquals(404, resposta.getStatusCode().value());

    }

    @Test
    void deveSalvarCliente() {
        Cliente novo = new Cliente();
        novo.setNome("Carla");

        when(service.salvar(novo)).thenReturn(novo);

        Cliente salvo = controller.adicionar(novo);

        assertEquals("Carla", salvo.getNome());
        verify(service, times(1)).salvar(novo);
    }

    @Test
    void deveDeletarCliente() {
        ResponseEntity<Void> resposta = controller.deletar(1L);
        assertEquals(204, resposta.getStatusCode().value());
        verify(service, times(1)).deletar(1L);
    }

    @Test
    void deveListarProdutosDoServiceApi() {
        ProdutoDTO produto = new ProdutoDTO();
        produto.setNome("Monitor");

        when(service.listarProdutosDoServiceApi()).thenReturn(List.of(produto));

        ResponseEntity<List<ProdutoDTO>> resposta = controller.listarProdutosDoServiceApi();

        // Linha Corrigida: De 204 para 200
        assertEquals(200, resposta.getStatusCode().value());

        assertEquals("Monitor", resposta.getBody().get(0).getNome());
    }
}