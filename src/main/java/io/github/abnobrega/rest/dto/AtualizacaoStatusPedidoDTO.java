package io.github.abnobrega.rest.dto;

public class AtualizacaoStatusPedidoDTO {
    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private String novoStatus;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public AtualizacaoStatusPedidoDTO() {
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }

    @Override
    public String toString() {
        return "AtualizacaoStatusPedidoDTO{" +
                "novoStatus='" + novoStatus + '\'' +
                '}';
    }
}
