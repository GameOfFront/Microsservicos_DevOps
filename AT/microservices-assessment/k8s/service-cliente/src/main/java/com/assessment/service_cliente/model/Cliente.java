package com.assessment.service_cliente.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("cliente")
public class Cliente {

    @Id
    private Long id;
    private String nome;
    private String email;
}
