package io.github.abnobrega.rest.dto;

public class CredenciaisDTO {
    /* DTO que representa o objeto de transferência com as credenciais do usuário,
        que serão enviadas na requisição, para fazer a autenticação do usuário.
        OBS: O usuário envia a requisição com suas credenciais, para ser autenticado no Spring Security
    */

    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private String login;
    private String senha;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public CredenciaisDTO(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
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
}
