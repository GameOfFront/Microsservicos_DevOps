package com.assessment.service_cliente;

import com.assessment.service_cliente.controller.ClienteController;
import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.model.ProdutoDTO;
import com.assessment.service_cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService service;

    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ClienteController controller = new ClienteController(service);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void deveListarClientes() {
        when(service.listarTodos()).thenReturn(Flux.just(new Cliente()));

        webTestClient.get()
                .uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cliente.class)
                .hasSize(1);
    }

    @Test
    void deveBuscarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Paulo");

        when(service.buscarPorId(1L)).thenReturn(Mono.just(cliente));

        webTestClient.get()
                .uri("/clientes/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cliente.class)
                .isEqualTo(cliente);
    }

    @Test
    void deveRetornar404QuandoNaoEncontrarCliente() {
        when(service.buscarPorId(2L)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/clientes/2")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deveSalvarCliente() {
        Cliente novo = new Cliente();
        novo.setNome("Carla");

        when(service.salvar(any())).thenReturn(Mono.just(novo));

        webTestClient.post()
                .uri("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(novo)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cliente.class)
                .isEqualTo(novo);
    }

    @Test
    void deveDeletarCliente() {
        when(service.deletar(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/clientes/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deveListarProdutosDoServiceApi() {
        ProdutoDTO produto = new ProdutoDTO();
        produto.setNome("Monitor");

        when(service.listarProdutosDoServiceApi()).thenReturn(Flux.just(produto));

        webTestClient.get()
                .uri("/clientes/produtos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProdutoDTO.class)
                .hasSize(1);
    }
}
