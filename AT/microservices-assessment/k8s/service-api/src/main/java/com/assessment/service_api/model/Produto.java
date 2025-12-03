package com.assessment.service_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("produto")
public class Produto {
    @Id
    private Long id;
    private String nome;
    private Double preco;
}
