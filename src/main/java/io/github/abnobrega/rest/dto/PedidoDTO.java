package io.github.abnobrega.rest.dto;

// DTO - Data Transfer Object: Padrão para mapear objetos simples.
// Recebemos o DTO via requisição e usamos seus dados para criamos nosso modelo de dados para persistir

/* Objeto JSON que será enviado via requisição com os dados para o DTO
{
    "cliente" : 1,
    "totalPedido": 100.00,
    // Passar um array de itens
    "itens" : [
        // Onde cada item é um objeto
        {
            "produto" : 1,
            "quantidade" : 10
        }
    ]
}
*/

import io.github.abnobrega.validation.NotEmptyList;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

// Se recebemos o DTO via requisição, temos que validar os dados do DTO também
public class PedidoDTO {
    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    //@NotNull(message = "Informe o código do cliente.")
    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer idCliente;
    @NotNull(message = "{campo.total-pedido.obrigatotio}")
    private BigDecimal totalPedido;
    @NotEmptyList(message = "{campo.itens-pedido.obrigatorio}")
    private List<ItemPedidoDTO> itens;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public PedidoDTO(Integer idCliente, BigDecimal totalPedido, List<ItemPedidoDTO> itens) {
        this.idCliente = idCliente;
        this.totalPedido = totalPedido;
        this.itens = itens;
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "idCliente=" + idCliente +
                ", totalPedido=" + totalPedido +
                ", itens=" + itens +
                '}';
    }
}
