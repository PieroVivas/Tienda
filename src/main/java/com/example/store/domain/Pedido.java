package com.example.store.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("pedidos")
public class Pedido {
    @Id
    @Column("id")
    private Long id;

    @Column("fecha")
    private LocalDateTime fecha;

    @Column("total")
    private Double total;

    @Column("estado")
    private String estado;
}
