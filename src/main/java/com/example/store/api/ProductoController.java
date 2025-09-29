package com.example.store.api;

import com.example.store.domain.Producto;
import com.example.store.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public Flux<Producto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> getById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Producto> create(@RequestBody Producto producto) {
        return service.save(producto);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Producto>> update(@PathVariable("id") Long id,
                                                 @RequestBody Producto producto) {
        return service.findById(id)
                .flatMap(existing -> {
                    existing.setNombre(producto.getNombre());
                    existing.setPrecio(producto.getPrecio());
                    existing.setStock(producto.getStock());
                    return service.save(existing);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id) {
        return service.findById(id)
                .flatMap(existing -> service.delete(existing).then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
