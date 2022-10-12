package io.github.abnobrega.service.impl;

import io.github.abnobrega.domain.entity.Cliente;
import io.github.abnobrega.domain.entity.ItemPedido;
import io.github.abnobrega.domain.entity.Pedido;
import io.github.abnobrega.domain.entity.Produto;
import io.github.abnobrega.domain.enums.StatusPedido;
import io.github.abnobrega.domain.exception.PedidoNaoEncontradoException;
import io.github.abnobrega.domain.exception.RegraNegocioException;
import io.github.abnobrega.domain.repository.ClientesDAO;
import io.github.abnobrega.domain.repository.ItensPedidoDAO;
import io.github.abnobrega.domain.repository.PedidosDAO;
import io.github.abnobrega.domain.repository.ProdutosDAO;
import io.github.abnobrega.rest.dto.ItemPedidoDTO;
import io.github.abnobrega.rest.dto.PedidoDTO;
import io.github.abnobrega.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {
    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private final PedidosDAO pedidosDAO;
    private final ClientesDAO clientesDAO;
    private final ProdutosDAO produtosDAO;
    private final ItensPedidoDAO itensPedidoDAO;


    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public PedidoServiceImpl(PedidosDAO pedidosDAO, ClientesDAO clientesDAO, ProdutosDAO produtosDAO, ItensPedidoDAO itensPedidoDAO) {
        this.pedidosDAO = pedidosDAO;
        this.clientesDAO = clientesDAO;
        this.produtosDAO = produtosDAO;
        this.itensPedidoDAO = itensPedidoDAO;
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************

    //*******************************************
    //************ C O N S U L T A R ************
    //*******************************************
    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosDAO.findByIdFetchItens(id);
    }

    //*******************************************
    //************** E X C L U I R **************
    //*******************************************

    //*******************************************
    //************** I N C L U I R **************
    //*******************************************
    @Override
    @Transactional



























































    public Pedido incluirPedido(PedidoDTO pedidoDTO) {
        // *** Trata o Cliente do Pedido
        Integer idCliente = pedidoDTO.getIdCliente(); // Ibtém o id do Cliente que fez o Pedido
        Cliente cliente = clientesDAO
                .findById(idCliente)
                .orElseThrow( ()->
                        new RegraNegocioException("Código de cliente inválido." + idCliente ));
                        //Se JSon enviar um id de cliente que não existe, vamos lançar uma exceção aqui

        // *** Trata o Pedido
        Pedido pedido = new Pedido();
        pedido.setTotalPedido(pedidoDTO.getTotalPedido());
        pedido.setDataPedido(LocalDate.now()); // Data corremte
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.REALIZADO);

        // *** Trata os Itens do Pedido
        List<ItemPedido> listaItensPedido = converterItensPedido(pedido, pedidoDTO.getItens());
        // Gravado o pedido no BD
        pedidosDAO.save(pedido);
        // Gravado a lista com os itens do pedido no BD
        itensPedidoDAO.saveAll(listaItensPedido);
        pedido.setItens(listaItensPedido);

        return pedido;
    }

    // Método privado desta classe
    // mapeando DE listaItensPedidoDTO PARA ItemPedido
    private List<ItemPedido> converterItensPedido(Pedido pedido, List<ItemPedidoDTO> listaItensPedidoDTO ){
        if (listaItensPedidoDTO.isEmpty()){
            throw new RegraNegocioException("Não é possível inluir pedido sem itens de pedido.");
        }

        // Transformando o formato Json em uma lista de objetos ItensPedido
        return listaItensPedidoDTO
                .stream()
                .map( itemPedidoDTO -> {
                    Integer idProduto = itemPedidoDTO.getIdProduto();
                    Produto produto = produtosDAO
                            .findById(idProduto)
                            .orElseThrow( ()->
                                    new RegraNegocioException("Código de produto inválido."+ idProduto ));
                                    //Se o JSon enviar um id de produto que não existe, vamos lançar uma exceção aqui

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());

                    return itemPedido;

                }).collect(Collectors.toList()); // Transformando a stream de DTOs em uma lista de itens Pedidos

    }

    //*******************************************
    //************ A T U A L I Z A R ************
    //*******************************************
    @Override
    @Transactional
    public void atualizarStatusPedido(Integer id, StatusPedido novoStatusPedido) {
        pedidosDAO
                .findById(id)
                .map( pedido -> {
                    // Encontrou o pedido. Logo, tenho que atualizar o status do pedido
                    pedido.setStatusPedido(novoStatusPedido);
                    return pedidosDAO.save(pedido);
                } ).orElseThrow( ()-> new PedidoNaoEncontradoException() );
    }

}
