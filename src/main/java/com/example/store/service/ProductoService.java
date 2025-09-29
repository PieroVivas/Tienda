package com.example.store.service;

import com.example.store.domain.Producto;
import com.example.store.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;

    public Flux<Producto> findAll() { return repository.findAll(); }
    public Mono<Producto> findById(Long id) { return repository.findById(id); }
    public Mono<Producto> save(Producto p) { return repository.save(p); }
    public Mono<Void> delete(Producto p) { return repository.delete(p); }
}
