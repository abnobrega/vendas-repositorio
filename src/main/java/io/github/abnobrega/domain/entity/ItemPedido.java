package io.github.abnobrega.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    //*********************************
    //******* A T R I B U T O S *******
    //*********************************
    @Id // Define a chave primária dessa entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o autoincremento essa entidade.
    @Column(name = "id_item_pedido")
    private Integer idItemPedido;

    // ITEM_PEDIDO n:1 PEDIDO
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    // ITEM_PEDIDO n:1 PRODUTO
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    //***************************************
    //******* C O N S T R U T O R E S *******
    //***************************************
    public ItemPedido(Integer idItemPedido, Pedido pedido, Produto produto, Integer quantidade) {
        this.idItemPedido = idItemPedido;
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
    }
    public ItemPedido() {
    }

    //*****************************
    //******* M É T O D O S *******
    //*****************************
    public Integer getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Integer idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItemPedido=" + idItemPedido +
                ", pedido=" + pedido +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                '}';
    }
}
