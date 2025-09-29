package com.example.store.mapper;

import com.example.store.domain.ItemPedido;
import com.example.store.domain.Pedido;
import com.example.store.dto.Item;
import com.example.store.dto.PedidoResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PedidoMapper {

    public PedidoResponse toResponse(Pedido pedido, List<ItemPedido> itemPedidos) {
        List<Item> items = itemPedidos.stream()
                .map(PedidoMapper::toItemDto)
                .toList();
        return PedidoResponse.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .total(pedido.getTotal())
                .estado(pedido.getEstado())
                .items(items)
                .build();
    }

    private Item toItemDto(ItemPedido itemPedido) {
        return Item.builder()
                .productoId(itemPedido.getProductoId())
                .cantidad(itemPedido.getCantidad())
                .subtotal(itemPedido.getSubtotal())
                .build();
    }
}
