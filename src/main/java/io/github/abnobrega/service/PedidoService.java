package io.github.abnobrega.service;

import io.github.abnobrega.domain.entity.Pedido;
import io.github.abnobrega.domain.enums.StatusPedido;
import io.github.abnobrega.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    //****************************************************
    //************** ASSINATURA DOS MÉTODOS **************
    //****************************************************

    //*******************************************
    //************ C O N S U L T A R ************
    //*******************************************
    Optional<Pedido> obterPedidoCompleto(Integer id);

    //*******************************************
    //************** E X C L U I R **************
    //*******************************************

    //*******************************************
    //************** I N C L U I R **************
    //*******************************************
    Pedido incluirPedido(PedidoDTO pedidoDTO);

    //*******************************************
    //************ A T U A L I Z A R ************
    //*******************************************
    void atualizarStatusPedido(Integer id, StatusPedido statusPedido);


}

