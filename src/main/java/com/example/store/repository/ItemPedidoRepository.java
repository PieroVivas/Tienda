package com.example.store.repository;

import com.example.store.domain.ItemPedido;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemPedidoRepository extends ReactiveCrudRepository<ItemPedido, Long> { }
