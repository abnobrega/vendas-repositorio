package io.github.abnobrega.rest.dto;

import java.math.BigDecimal;
import java.util.List;


public class InformacoesPedidoDTO {
    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private String dataPedido;
    private String statusPedido;
    private BigDecimal totalPedido;
    private List<InformacoesItemPedidoDTO> informacoesItemPedidoDTO;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public InformacoesPedidoDTO(Integer codigo, String cpf, String nomeCliente, String dataPedido, String statusPedido , BigDecimal totalPedido, List<InformacoesItemPedidoDTO> informacoesItemPedidoDTO) {
        this.codigo = codigo;
        this.cpf = cpf;
        this.nomeCliente = nomeCliente;
        this.dataPedido = dataPedido;
        this.statusPedido = statusPedido;
        this.totalPedido = totalPedido;
        this.informacoesItemPedidoDTO = informacoesItemPedidoDTO;
    }

    public InformacoesPedidoDTO() {
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<InformacoesItemPedidoDTO> getInformacoesItemPedidoDTO() {
        return informacoesItemPedidoDTO;
    }

    public void setInformacoesItemPedidoDTO(List<InformacoesItemPedidoDTO> informacoesItemPedidoDTO) {
        this.informacoesItemPedidoDTO = informacoesItemPedidoDTO;
    }

    @Override
    public String toString() {
        return "InformacoesPedidoDTO{" +
                "codigo=" + codigo +
                ", cpf='" + cpf + '\'' +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", dataPedido='" + dataPedido + '\'' +
                ", statusPedido='" + statusPedido + '\'' +
                ", totalPedido=" + totalPedido +
                ", informacoesItemPedidoDTO=" + informacoesItemPedidoDTO +
                '}';
    }
}
