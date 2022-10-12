package io.github.abnobrega.rest.controller;

import com.sun.net.httpserver.Headers;
import io.github.abnobrega.domain.entity.Cliente;
import io.github.abnobrega.domain.repository.ClientesDAO;
import io.swagger.annotations.*;
import net.bytebuddy.asm.Advice;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import java.util.Optional;

// Annotation '@Controller' que define o Bean Gerenciado do Spring, com esteriótipo de CONTROLE,
// que é a "Camada de Cliente" que vai se comunicar com os Clientes. Esse Bean passa a ser
// gerenciado pelo Spring Container IOC (INVERSION OF CONTROL), que é o Container de Injeção
// de Dependência (DI/DC) do Spring e passa a controlar o recebimento das requisições HTTP.
@RestController
@RequestMapping("/api/clientes") // URL base do Controlador Cliente
@Api("Api Clientes") // String com o nome dessa API
public class ClienteController {
    // Essa classe é um controlador REST, que vai receber requisições HTTP
    // e vai se comunicar dentro da arquitetura REST

    //*********************************
    //******* A T R I B U T O S *******
    //*********************************
    private ClientesDAO clientesDAO;

    //***********************************
    //******* C O N S T R U T O R *******
    //***********************************
    public ClienteController(ClientesDAO clientes) {
        this.clientesDAO = clientes;
    }
    //***********************************************
    //**************** M É T O D O S ****************
    //***********************************************

    //***********************************************
    //************** C O N S U L T A R **************
    //***********************************************
    @GetMapping("{id}")
    @ApiOperation("Consultar Clientes por Id") // Anotation que diz a operação que será executada
    @ApiResponses({     // @ApiResponses porque tem mais de uma resposta: OK ou NotFound
        // Array contendo cada resposta da API
        @ApiResponse(code = 200, message = "Cliente Encontrado"),
        @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public Cliente getClienteById(
            @PathVariable
            @ApiParam("Id do cliente") Integer id){
        return clientesDAO
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente Não Encontrado") );
    }

    @GetMapping
    public List<Cliente> findCliente(Cliente filtroCliente){
        // Objeto ExampleMatcher que permite identificar um objeto através das suas propriedades
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtroCliente, matcher);
        return clientesDAO.findAll(example);
    }

    //*******************************************
    //************** E X C L U I R **************
    //*******************************************
    @DeleteMapping("{id}")  //OBS: Essa URL é o endereço de um recurso
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Excluir Cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Exclusão realizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public void excluirCliente(
            @PathVariable
            @ApiParam("Id do cliente") Integer id ){
        clientesDAO
                .findById(id)
                .map( cliente -> {
                    clientesDAO.delete(cliente);
                    return cliente;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente Não Encontrado") );
    }

    //*******************************************
    //************** I N C L U I R **************
    //*******************************************
    @PostMapping // Rota para chegar até aqui
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Incluir Cliente")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Inclusão realizada com sucesso"),
        @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Cliente incluirCliente( @RequestBody @Valid Cliente cliente ){
        // O cliente é um obj que vai vir no corpo da requisição, que vamos receber no formato JSON.
        // Logo, tenho que mapear o cliente no corpo da requisição.
        return clientesDAO.save(cliente);
    }

    //*******************************************
    //************ A T U A L I Z A R ************
    //*******************************************
    //@RequestMapping( method = RequestMethod.PUT)
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualizar Cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Atualização realizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public void atualizarCliente( @PathVariable
                                  @ApiParam("Id do cliente") Integer id,
                                  @RequestBody @Valid Cliente clienteNovo ) {
        clientesDAO
                .findById(id) // Busco o cliente existente no banco de dados com esse id
                .map( clienteExistenteBD -> {
                    clienteNovo.setIdCliente( clienteExistenteBD.getIdCliente() );  // Atribuo no Novo Cliente o id do cliente existente no banco de dados
                    clientesDAO.save(clienteNovo); // Atualizo o cliente com os novos dados
                    return clienteExistenteBD;     // retorno 200 - Sucesso
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente Não Encontrado") ); // retorno 404 - Not Found
    }

}
