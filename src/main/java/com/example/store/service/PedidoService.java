package com.example.store.service;

import com.example.store.domain.ItemPedido;
import com.example.store.domain.Pedido;
import com.example.store.dto.PedidoRequest;
import com.example.store.repository.ItemPedidoRepository;
import com.example.store.repository.PedidoRepository;
import com.example.store.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public Mono<Pedido> confirmarPedido(PedidoRequest request) {
        Pedido pedido = Pedido.builder()
                .fecha(LocalDateTime.now())
                .total(0.0)
                .estado("CONFIRMADO")
                .build();

        return pedidoRepository.save(pedido)
                .flatMap(saved ->
                        Flux.fromIterable(request.getItems())
                                .flatMap(reqItem ->
                                        productoRepository.findById(reqItem.getProductoId())
                                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Producto no existe: " + reqItem.getProductoId())))
                                                .flatMap(prod -> {
                                                    if (prod.getStock() < reqItem.getCantidad()) {
                                                        return Mono.error(new IllegalStateException("Stock insuficiente para producto " + prod.getId()));
                                                    }

                                                    int nuevoStock = prod.getStock() - reqItem.getCantidad();

                                                    // 🔹 Intento de actualización con bloqueo optimista
                                                    return productoRepository.updateStockConOptimismo(prod.getId(), nuevoStock, prod.getVersion())
                                                            .flatMap(rowsUpdated -> {
                                                                if (rowsUpdated == 0) {
                                                                    return Mono.error(new IllegalStateException("Conflicto de concurrencia: El stock del producto fue modificado por otra transacción"));
                                                                }
                                                                double subtotal = prod.getPrecio() * reqItem.getCantidad();
                                                                ItemPedido item = ItemPedido.builder()
                                                                        .pedidoId(saved.getId())
                                                                        .productoId(prod.getId())
                                                                        .cantidad(reqItem.getCantidad())
                                                                        .subtotal(subtotal)
                                                                        .build();
                                                                return itemPedidoRepository.save(item);
                                                            });
                                                })
                                )
                                .collectList()
                                .flatMap(items -> {
                                    double total = items.stream().mapToDouble(ItemPedido::getSubtotal).sum();
                                    saved.setTotal(total);
                                    return pedidoRepository.save(saved).thenReturn(saved);
                                })
                );
    }

    public Mono<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Mono<List<ItemPedido>> itemsByPedido(Long pedidoId) {
        return itemPedidoRepository.findAll()
                .filter(i -> i.getPedidoId().equals(pedidoId))
                .collectList();
    }
}
