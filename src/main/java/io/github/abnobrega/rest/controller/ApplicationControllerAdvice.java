package io.github.abnobrega.rest.controller;

import io.github.abnobrega.domain.exception.PedidoNaoEncontradoException;
import io.github.abnobrega.domain.exception.RegraNegocioException;
import io.github.abnobrega.rest.ApiErros;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/* @ControllerAdvice is a specialization of the @Component annotation which allows
   to handle exceptions across the whole application in one global handling component.
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {
    // Os exceptions handles são métodos que permitem fazer um tratamento do erro,
    // retornando um código de status e  mensagem de erro correta para meu cliente.

    //marca esse método para ser um tratador de erros
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  //Equivale ao código de status 400
    public ApiErros handleRegraNegocioException(RegraNegocioException exception){
        // Toda vez que meu projeto lançar uma RegraNegocioException, ele vai cair
        // dentro desse ExceptionHandler para que eu posso tratar o erro.
        String mensagemErro = exception.getMessage();
        return new ApiErros(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handlePedidoNotFoundException(PedidoNaoEncontradoException exception){
        return new ApiErros(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleMethodNotValidException(MethodArgumentNotValidException exception) {
        //Objeto que carrega os dados de validação e o que falhou
        List<String> erros = exception.getBindingResult().getAllErrors()
                .stream()
                .map( erro -> erro.getDefaultMessage() )
                .collect(Collectors.toList());
                // Com isso tenho uma lista de mensagens que foram lançadas no momento do erro, na validaçaõ do campo
        return new ApiErros(erros);
    }

}
