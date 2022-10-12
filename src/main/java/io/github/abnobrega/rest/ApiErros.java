package io.github.abnobrega.rest;

import jdk.jfr.DataAmount;

import java.util.Arrays;
import java.util.List;

public class ApiErros {
    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private List<String> erros;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public ApiErros(List<String> erros) {
        // Construtor de lista de erros: um array de strings
        this.erros = erros;
    }

    public ApiErros(String mensagenErro){
        // Construtor que recebe apenas uma mensagem
        this.erros = Arrays.asList(mensagenErro);
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }


}
