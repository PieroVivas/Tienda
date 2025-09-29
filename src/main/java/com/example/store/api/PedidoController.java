package com.example.store.api;

import com.example.store.domain.Pedido;
import com.example.store.domain.ItemPedido;
import com.example.store.dto.PedidoRequest;
import com.example.store.dto.PedidoResponse;
import com.example.store.mapper.PedidoMapper;
import com.example.store.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping("/confirmar")
    public Mono<ResponseEntity<PedidoResponse>> confirmar(@RequestBody PedidoRequest request) {
        return service.confirmarPedido(request)
                .flatMap(saved -> service.itemsByPedido(saved.getId())
                        .map(items -> PedidoMapper.toResponse(saved, items)))
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PedidoResponse>> getById(@PathVariable("id") Long id) {
        return service.findById(id)
                .flatMap(p -> service.itemsByPedido(p.getId())
                        .map(items -> PedidoMapper.toResponse(p, items)))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
