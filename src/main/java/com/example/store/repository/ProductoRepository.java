package com.example.store.repository;

import com.example.store.domain.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {

    @Query("UPDATE producto SET stock = :stock, version = version + 1 WHERE id = :id AND version = :version")
    Mono<Integer> updateStockConOptimismo(Long id, Integer stock, Integer version);

}
