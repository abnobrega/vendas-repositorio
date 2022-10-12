package io.github.abnobrega.domain.repository;

import io.github.abnobrega.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidoDAO extends JpaRepository<ItemPedido, Integer> {


}
