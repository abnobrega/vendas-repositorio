package io.github.abnobrega.rest.dto;

import java.math.BigDecimal;

public class InformacoesItemPedidoDTO {
    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private String descricaProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public InformacoesItemPedidoDTO(String descricaProduto, BigDecimal precoUnitario, Integer quantidade) {
        this.descricaProduto = descricaProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public InformacoesItemPedidoDTO() {
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    public String getDescricaProduto() {
        return descricaProduto;
    }

    public void setDescricaProduto(String descricaProduto) {
        this.descricaProduto = descricaProduto;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "InformacaoItemPedidoDTO{" +
                "descricaProduto='" + descricaProduto + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", quantidade=" + quantidade +
                '}';
    }
}
