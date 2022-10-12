package io.github.abnobrega.domain.repository;

import io.github.abnobrega.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosDAO extends JpaRepository<Produto, Integer> {

}
