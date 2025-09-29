package com.example.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("items_pedido")
public class ItemPedido {
    @Id
    @Column("id")
    private Long id;

    @Column("pedido_id")
    private Long pedidoId;

    @Column("producto_id")
    private Long productoId;

    @Column("cantidad")
    private Integer cantidad;

    @Column("subtotal")
    private Double subtotal;
}
