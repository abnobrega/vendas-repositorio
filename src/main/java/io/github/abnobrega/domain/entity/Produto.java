package io.github.abnobrega.domain.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {

    //*********************************
    //******* A T R I B U T O S *******
    //*********************************
    @Id // Define a chave primária dessa entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o autoincremento essa entidade.
    @Column(name = "id_produto")
    private Integer idProduto;

    @Column(name = "descricao")
    @NotEmpty(message = "{campo.descricao-produto.obrigatorio}")
    private String descricao;

    @Column(name = "preco_unitario", precision = 20, scale = 2)
    @Type(type = "big_decimal")
    @NotNull(message = "{campo.preco-unit-produto.obrigatorio}")
    private BigDecimal precoUnitario;

    //***************************************
    //******* C O N S T R U T O R E S *******
    //***************************************
    public Produto(Integer idProduto, String descricao, BigDecimal precoUnitario) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
    }

    public Produto(String descricao, BigDecimal precoUnitario) {
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
    }

    public Produto() {
    }

    //*****************************
    //******* M É T O D O S *******
    //*****************************
    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", descricao='" + descricao + '\'' +
                ", precoUnitario=" + precoUnitario +
                '}';
    }
}
