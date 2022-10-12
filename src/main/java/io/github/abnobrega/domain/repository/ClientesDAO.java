package io.github.abnobrega.domain.repository;

import ch.qos.logback.core.net.server.Client;
import io.github.abnobrega.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
// ****************************************************************************
// ************************** CAMADA DE PERSISTÊNCIA **************************
// ****************************************************************************


// CLASSE DAO da CAMADA DE NEGÓCIOS, que encapsula todas as operações/serviços (DML)
// de acesso à base de dados, referentes à classe de negócio Cliente: Entidade Cliente do Modelo de Negócios,
//*****************************************************************************
// ************** CLIENTE DAO com a interface do SPRING DATA JPA **************
//*****************************************************************************

public interface ClientesDAO extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeLike(String nome);
        // Equivale a SELECT c FROM Cliente WHERE c.nome like :nome

    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome( @Param("nome") String nome );

    @Query("delete from Cliente c where c.nome =:nome")
    @Modifying
    void deleteByNome(String nome);

    boolean existsByNome(String nome);

    // Carregando os clientes com os pedidos
    @Query(" select c from Cliente c left join fetch c.pedidos WHERE c.idCliente =:id ")
    Cliente findClienteFetchPedidos( @Param("id") Integer idCliente );

}
