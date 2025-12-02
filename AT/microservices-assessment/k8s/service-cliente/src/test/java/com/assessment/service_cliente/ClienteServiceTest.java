package com.assessment.service_cliente;

import com.assessment.service_cliente.model.Cliente;
import com.assessment.service_cliente.model.ProdutoDTO;
import com.assessment.service_cliente.repository.ClienteRepository;
import com.assessment.service_cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClienteService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodosClientes() {
        when(repository.findAll()).thenReturn(List.of(new Cliente()));

        List<Cliente> clientes = service.listarTodos();

        assertEquals(1, clientes.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarPorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ana");
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = service.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNome());
    }

    @Test
    void deveSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Pedro");
        when(repository.save(cliente)).thenReturn(cliente);

        Cliente salvo = service.salvar(cliente);

        assertEquals("Pedro", salvo.getNome());
        verify(repository, times(1)).save(cliente);
    }

    @Test
    void deveDeletarCliente() {
        service.deletar(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveListarProdutosDoServiceApi() {
        ProdutoDTO produto = new ProdutoDTO();
        produto.setId(1L);
        produto.setNome("Cadeira");
        produto.setPreco(500.0);

        when(restTemplate.getForObject(anyString(), eq(ProdutoDTO[].class)))
                .thenReturn(new ProdutoDTO[]{produto});

        List<ProdutoDTO> resultado = service.listarProdutosDoServiceApi();

        assertEquals(1, resultado.size());
        assertEquals("Cadeira", resultado.get(0).getNome());
    }

    @Test
    void deveLancarExcecaoAoFalharNaRequisicao() {
        when(restTemplate.getForObject(anyString(), eq(ProdutoDTO[].class)))
                .thenThrow(new RuntimeException("Erro 500"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listarProdutosDoServiceApi());
        assertTrue(ex.getMessage().contains("Erro ao buscar produtos"));
    }
}
