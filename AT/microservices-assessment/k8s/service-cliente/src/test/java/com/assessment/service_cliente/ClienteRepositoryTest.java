package com.assessment.service_cliente;

import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    void deveSalvarEListarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");

        repository.save(cliente);

        List<Cliente> clientes = repository.findAll();
        assertEquals(1, clientes.size());
        assertEquals("Maria", clientes.get(0).getNome());
    }

    @Test
    void deveBuscarPorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEmail("joao@email.com");
        Cliente salvo = repository.save(cliente);

        Optional<Cliente> encontrado = repository.findById(salvo.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("João", encontrado.get().getNome());
    }

    @Test
    void deveExcluirCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos");
        cliente.setEmail("carlos@email.com");
        Cliente salvo = repository.save(cliente);

        repository.deleteById(salvo.getId());
        assertTrue(repository.findAll().isEmpty());
    }
}
