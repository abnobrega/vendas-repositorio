package io.github.abnobrega.domain.repository;

import io.github.abnobrega.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    // QueryMethod para carregar o usuário pelo username (login)
    Optional<Usuario> findByLogin(String login);

}
