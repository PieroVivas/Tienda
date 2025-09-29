package com.example.store.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PedidoResponse {
    private Long id;
    private LocalDateTime fecha;
    private Double total;
    private String estado;
    private List<Item> items;
}
