package com.example.store.repository;

import com.example.store.domain.Pedido;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PedidoRepository extends ReactiveCrudRepository<Pedido, Long> { }
