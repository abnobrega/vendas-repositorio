package io.github.abnobrega.domain.entity;

public class ClienteOLD {

    //*********************************
    //******* A T R I B U T O S *******
    //*********************************
    private Integer idCliente;
    private String nome;

    //***************************************
    //******* C O N S T R U T O R E S *******
    //***************************************
    public ClienteOLD(Integer idCliente, String nome) {
        this.idCliente = idCliente;
        this.nome = nome;
    }

    public ClienteOLD(String nome) {
        this.nome = nome;
    }

    public ClienteOLD() {
    }

    //*****************************
    //******* M É T O D O S *******
    //*****************************
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nome='" + nome + '\'' +
                '}';
    }
}
