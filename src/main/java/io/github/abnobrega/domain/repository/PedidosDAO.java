package io.github.abnobrega.domain.repository;

import io.github.abnobrega.domain.entity.Cliente;
import io.github.abnobrega.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidosDAO extends JpaRepository<Pedido, Integer> {
    // Obter os Pedidos de um determinado cliente
    List<Pedido> findByCliente(Cliente cliente);

    // Obter os Itens do Pedido

    @Query(" select p from Pedido p left join fetch p.itens where p.id = :id ")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
