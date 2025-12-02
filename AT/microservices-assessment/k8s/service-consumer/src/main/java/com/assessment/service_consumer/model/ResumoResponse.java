package com.assessment.service_consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResumoResponse {
    private int totalProdutos;
    private int totalClientes;
    private List<Produto> produtos;
    private List<Cliente> clientes;
}
