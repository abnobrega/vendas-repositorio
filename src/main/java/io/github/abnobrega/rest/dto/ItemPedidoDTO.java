package io.github.abnobrega.rest.dto;

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

public class ItemPedidoDTO {
    //*********************************
    //******* A T R I B U T O S *******
    //*********************************
    private Integer IdProduto;
    private Integer quantidade;

    //***************************************
    //******* C O N S T R U T O R E S *******
    //***************************************
    public ItemPedidoDTO(Integer idProduto, Integer quantidade) {
        IdProduto = idProduto;
        this.quantidade = quantidade;
    }

    //*****************************
    //******* M É T O D O S *******
    //*****************************
    public Integer getIdProduto() {
        return IdProduto;
    }

    public void setIdProduto(Integer idProduto) {
        IdProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemPedidoDTO{" +
                "IdProduto=" + IdProduto +
                ", quantidade=" + quantidade +
                '}';
    }
}
