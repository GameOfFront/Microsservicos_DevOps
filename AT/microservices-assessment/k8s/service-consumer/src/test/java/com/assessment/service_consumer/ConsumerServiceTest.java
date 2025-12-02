package com.assessment.service_consumer;

import com.assessment.service_consumer.model.Cliente;
import com.assessment.service_consumer.model.Produto;
import com.assessment.service_consumer.model.ResumoResponse;
import com.assessment.service_consumer.service.ConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceTest {

    // Mock do RestTemplate, que simula as chamadas HTTP externas
    @Mock
    private RestTemplate restTemplate;

    // Injeta os mocks no objeto de serviço que queremos testar
    @InjectMocks
    private ConsumerService consumerService;

    private static final String MOCK_API_URL = "http://mock-api:8081";
    private static final String MOCK_CLIENTE_URL = "http://mock-cliente:8082";

    @BeforeEach
    void setUp() {
        // Injeta os valores das URLs, pois o @Value não funciona em testes unitários simples
        ReflectionTestUtils.setField(consumerService, "serviceApiUrl", MOCK_API_URL);
        ReflectionTestUtils.setField(consumerService, "serviceClienteUrl", MOCK_CLIENTE_URL);
    }

    @Test
    void deveListarProdutosCorretamente() {
        // Cenário: Mocka a resposta do restTemplate para a chamada de produtos
        Produto[] produtosArray = {
                new Produto(1L, "Laptop", 1200.0),
                new Produto(2L, "Mouse", 25.0)
        };
        when(restTemplate.getForObject(eq(MOCK_API_URL + "/produtos"), eq(Produto[].class)))
                .thenReturn(produtosArray);

        // Ação
        List<Produto> produtos = consumerService.listarProdutos();

        // Verificação
        assertNotNull(produtos);
        assertEquals(2, produtos.size());
        assertEquals("Laptop", produtos.get(0).getNome());
        // Garante que o método getForObject foi chamado
        verify(restTemplate).getForObject(eq(MOCK_API_URL + "/produtos"), eq(Produto[].class));
    }

    @Test
    void deveCriarProdutoCorretamente() {
        // Cenário
        Produto produtoNovo = new Produto(null, "Teclado", 50.0);
        Produto produtoSalvo = new Produto(3L, "Teclado", 50.0);

        when(restTemplate.postForObject(eq(MOCK_API_URL + "/produtos"), eq(produtoNovo), eq(Produto.class)))
                .thenReturn(produtoSalvo);

        // Ação
        Produto resultado = consumerService.criarProduto(produtoNovo);

        // Verificação
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Teclado", resultado.getNome());
    }

    @Test
    void deveListarClientesCorretamente() {
        // Cenário: Mocka a resposta do restTemplate para a chamada de clientes
        Cliente[] clientesArray = {
                new Cliente(10L, "Alice", "alice@email.com"),
                new Cliente(11L, "Bob", "bob@email.com")
        };
        when(restTemplate.getForObject(eq(MOCK_CLIENTE_URL + "/clientes"), eq(Cliente[].class)))
                .thenReturn(clientesArray);

        // Ação
        List<Cliente> clientes = consumerService.listarClientes();

        // Verificação
        assertNotNull(clientes);
        assertEquals(2, clientes.size());
        assertEquals("Alice", clientes.get(0).getNome());
    }

    @Test
    void deveCriarClienteCorretamente() {
        // Cenário
        Cliente clienteNovo = new Cliente(null, "Charlie", "charlie@email.com");
        Cliente clienteSalvo = new Cliente(12L, "Charlie", "charlie@email.com");

        when(restTemplate.postForObject(eq(MOCK_CLIENTE_URL + "/clientes"), eq(clienteNovo), eq(Cliente.class)))
                .thenReturn(clienteSalvo);

        // Ação
        Cliente resultado = consumerService.criarCliente(clienteNovo);

        // Verificação
        assertNotNull(resultado);
        assertEquals(12L, resultado.getId());
        assertEquals("Charlie", resultado.getNome());
    }

    @Test
    void deveRetornarResumoGeralCorretamente() {
        // Cenário: Mocka as duas chamadas de dependência
        Produto[] produtosArray = { new Produto(1L, "P1", 10.0) };
        Cliente[] clientesArray = { new Cliente(1L, "C1", "c1@e.com"), new Cliente(2L, "C2", "c2@e.com") };

        when(restTemplate.getForObject(eq(MOCK_API_URL + "/produtos"), eq(Produto[].class)))
                .thenReturn(produtosArray);
        when(restTemplate.getForObject(eq(MOCK_CLIENTE_URL + "/clientes"), eq(Cliente[].class)))
                .thenReturn(clientesArray);

        // Ação
        ResumoResponse resumo = consumerService.resumoGeral();

        // Verificação
        assertNotNull(resumo);
        assertEquals(1, resumo.getTotalProdutos());
        assertEquals(2, resumo.getTotalClientes());
        assertEquals("P1", resumo.getProdutos().get(0).getNome());
        assertEquals("C1", resumo.getClientes().get(0).getNome());
    }
}