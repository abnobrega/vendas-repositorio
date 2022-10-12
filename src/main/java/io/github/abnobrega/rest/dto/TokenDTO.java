package io.github.abnobrega.rest.dto;

public class TokenDTO {
    /* DTO para representa o objeto de transferência com o retorno para o usuário autenticado.
       OBS: Se o usuário for autenticado, retornamos um TokenDTO.
    */
    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private String login;
    private String token;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public TokenDTO(String login, String token) {
        this.login = login;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
