package com.assessment.service_cliente;

import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.model.ProdutoDTO;
import com.assessment.service_cliente.repository.ClienteRepository;
import com.assessment.service_cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private WebClient webClient;

    private ClienteService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new ClienteService(repository, webClient);
    }

    @Test
    void deveListarTodosClientes() {
        when(repository.findAll()).thenReturn(Flux.just(new Cliente()));

        StepVerifier.create(service.listarTodos())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void deveBuscarPorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ana");

        when(repository.findById(1L)).thenReturn(Mono.just(cliente));

        StepVerifier.create(service.buscarPorId(1L))
                .expectNextMatches(c -> c.getNome().equals("Ana"))
                .verifyComplete();
    }

    @Test
    void deveSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Pedro");

        when(repository.save(any())).thenReturn(Mono.just(cliente));

        StepVerifier.create(service.salvar(cliente))
                .expectNextMatches(c -> c.getNome().equals("Pedro"))
                .verifyComplete();
    }

    @Test
    void deveDeletarCliente() {
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(service.deletar(1L))
                .verifyComplete();
    }
}
