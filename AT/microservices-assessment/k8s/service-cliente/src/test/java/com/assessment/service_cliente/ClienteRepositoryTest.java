package com.assessment.service_cliente;

import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach; // Importação adicionada
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    // Adicionado: Garante que o banco H2 esteja limpo antes de cada teste
    @BeforeEach
    void setup() {
        // Usamos block() para garantir que a operação reativa de exclusão termine antes
        // do próximo teste começar.
        repository.deleteAll().block();
    }

    @Test
    void deveSalvarEListarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");

        repository.save(cliente)
                .thenMany(repository.findAll())
                .as(StepVerifier::create)
                .expectNextMatches(c -> c.getNome().equals("Maria"))
                .verifyComplete();
    }

    @Test
    void deveExcluirCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos");
        cliente.setEmail("carlos@email.com");

        repository.save(cliente)
                .flatMap(saved -> repository.deleteById(saved.getId()))
                .thenMany(repository.findAll())
                .as(StepVerifier::create)
                .expectComplete() // Agora deve ser completo (vazio), pois a tabela foi limpa
                .verify();
    }
}