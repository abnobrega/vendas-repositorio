package io.github.abnobrega.domain.entity;

import io.github.abnobrega.domain.enums.StatusPedido;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    @Id // Define a chave primária dessa entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o autoincremento essa entidade.
    @Column(name = "id_pedido")
    private Integer idPedido;

    // PEDIDO n:1 CLIENTE (FK)
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    //Exemplo: 10000.00
    @Column(name = "total_pedido", precision = 20, scale = 2)
    @Type(type = "big_decimal")
    private BigDecimal totalPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusPedido statusPedido;

    //Fazer uma referência ao relacionamento com a tabela de clientes
    @OneToMany( mappedBy = "pedido")
    private List<ItemPedido> itens;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public Pedido(Integer idPedido, Cliente cliente, LocalDate dataPedido, BigDecimal totalPedido, StatusPedido statusPedido, List<ItemPedido> itens) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.totalPedido = totalPedido;
        this.statusPedido = statusPedido;
        this.itens = itens;
    }

    public Pedido() {
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", cliente=" + cliente +
                ", dataPedido=" + dataPedido +
                ", totalPedido=" + totalPedido +
                ", statusPedido=" + statusPedido +
                ", itens=" + itens +
                '}';
    }
}
