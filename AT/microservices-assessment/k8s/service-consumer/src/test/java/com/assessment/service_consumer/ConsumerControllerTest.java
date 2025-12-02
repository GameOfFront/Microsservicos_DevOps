package com.assessment.service_consumer;

import com.assessment.service_consumer.controller.ConsumerController;
import com.assessment.service_consumer.model.Cliente;
import com.assessment.service_consumer.model.Produto;
import com.assessment.service_consumer.model.ResumoResponse;
import com.assessment.service_consumer.service.ConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 🎯 Foca apenas nos componentes Web (Controller)
@WebMvcTest(ConsumerController.class)
@ActiveProfiles("test") // ✅ garante que use o application-test.properties
class ConsumerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // 🔗 Mocka o Serviço que é uma dependência do Controller
    @MockBean
    private ConsumerService consumerService;

    private final Produto PRODUTO_MOCK = new Produto(1L, "P1", 10.0);
    private final Cliente CLIENTE_MOCK = new Cliente(10L, "C1", "c1@e.com");

    @Test
    void deveListarProdutosERetornarOk() throws Exception {
        // Cenário
        List<Produto> produtos = Arrays.asList(PRODUTO_MOCK);
        when(consumerService.listarProdutos()).thenReturn(produtos);

        // Ação & Verificação
        mockMvc.perform(get("/consumer/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("P1"));
    }

    @Test
    void deveCriarProdutoERetornarOk() throws Exception {
        // Cenário
        when(consumerService.criarProduto(any(Produto.class))).thenReturn(PRODUTO_MOCK);

        // Ação & Verificação
        mockMvc.perform(post("/consumer/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PRODUTO_MOCK)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveListarClientesERetornarOk() throws Exception {
        // Cenário
        List<Cliente> clientes = Arrays.asList(CLIENTE_MOCK);
        when(consumerService.listarClientes()).thenReturn(clientes);

        // Ação & Verificação
        mockMvc.perform(get("/consumer/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("C1"));
    }

    @Test
    void deveCriarClienteERetornarOk() throws Exception {
        // Cenário
        when(consumerService.criarCliente(any(Cliente.class))).thenReturn(CLIENTE_MOCK);

        // Ação & Verificação
        mockMvc.perform(post("/consumer/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CLIENTE_MOCK)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L));
    }

    @Test
    void deveRetornarResumoGeralERetornarOk() throws Exception {
        // Cenário
        ResumoResponse resumo = new ResumoResponse(1, 1, Arrays.asList(PRODUTO_MOCK), Arrays.asList(CLIENTE_MOCK));
        when(consumerService.resumoGeral()).thenReturn(resumo);

        // Ação & Verificação
        mockMvc.perform(get("/consumer/resumo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalProdutos").value(1))
                .andExpect(jsonPath("$.totalClientes").value(1));
    }
}
