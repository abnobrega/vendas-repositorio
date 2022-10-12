package io.github.abnobrega.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity // Mapeamento para o JPA
@Table(name = "usuario")
public class Usuario {

    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    @Id // Define a chave primária dessa entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o autoincremento essa entidade
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;

    @Column
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String senha;

    @Column
    private boolean admin;
    // atribuito para saber quais são as roles ou as authorities do usuário

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public Usuario(Integer idUsuario, String login, String senha, boolean admin) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.admin = admin;
    }

    public Usuario() {
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", admin=" + admin +
                '}';
    }
}
