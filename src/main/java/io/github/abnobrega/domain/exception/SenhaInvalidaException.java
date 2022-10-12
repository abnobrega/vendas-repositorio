package io.github.abnobrega.domain.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha inválida."); // Sobrecarregando o construtor padrõa
    }
}
