package io.github.abnobrega.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.catalina.LifecycleState;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

//************************************
// ************** J P A **************
//************************************
@Entity
@Table( name = "cliente" )
//@Table(name ="tb_cliente", schema = "vendas") se o nome da tabele for <> da classe
public class Cliente {

    //*********************************
    //******* A T R I B U T O S *******
    //*********************************
    @Id // Define a chave primária dessa entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o autoincremento essa entidade.
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nome", length=100)
    //@NotEmpty(message = "Campo nome é obrigatório.")
    @NotEmpty(message = "{campo.nome-cliente.obrigatorio}")
    private String nome;

    @Column(name = "cpf", length=11)
    @NotEmpty(message = "{campo.cpf-cliente.obrigatorio}")
    @CPF(message = "{campo.cpf-cliente.invalido}") // Validador específico para validar o CPF
    private String cpf;

    // CLIENTE 1:n PEDIDO (Através da proriedade "cliente", que é PF na classe "Pedidos")
    @JsonIgnore // Informa para o parse OBJ <=> JSON que ignore esse atributo no JSON
    @OneToMany( mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Pedido> pedidos;

    //***************************************
    //******* C O N S T R U T O R E S *******
    //***************************************
    public Cliente(Integer idCliente, String nome) {
        this.idCliente = idCliente;
        this.nome = nome;
    }

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Cliente() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nome='" + nome + '\'' +
                '}';
    }
}
