package io.github.abnobrega.domain.exception;

public class PedidoNaoEncontradoException extends RuntimeException{
    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado.");
    }
}
