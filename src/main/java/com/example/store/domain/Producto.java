package com.example.store.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("producto")
public class Producto {
    @Id
    @Column("id")
    private Long id;

    @Column("nombre")
    private String nombre;

    @Column("precio")
    private Double precio;

    @Column("stock")
    private Integer stock;

    @Column("version")
    private Integer version;
}
