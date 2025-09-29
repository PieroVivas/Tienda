package com.example.store.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.util.List;

@Data
public class PedidoRequest {
    @NotNull
    private List<ItemReq> items;

    @Data
    public static class ItemReq {
        @NotNull
        private Long productoId;
        @Min(0)
        private Integer cantidad;
    }
}
