package io.github.abnobrega.rest.controller;

import io.github.abnobrega.domain.entity.Cliente;
import io.github.abnobrega.domain.repository.ClientesDAO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Annotation '@Controller' que define o Bean Gerenciado do Spring, com esteriótipo de CONTROLE,
// que é a "Camada de Cliente": que vai se comunicar com os Clientes. Esse Bean passa a ser
// gerenciado pelo Spring Container IOC (INVERSION OF CONTROL), que é o Container de Injeção
// de Dependência (DI/DC) do Spring e passa a controlar o recebimento das requisições HTTP.
//@Controller

// Definir a URL de acesso às rotas desse controller: URL base das API de Cliente.
// OBS: Toda requisição para essa URL específica vai "cair" nesse Controller de Cliente.
//@RequestMapping("/api/clientes") // URL base desse Controlador

/*
public class OLDClienteControllerVersao01 {
    // Essa classe é um controlador REST, que vai receber requisições HTTP
    // e vai se comunicar dentro da arquitetura REST

    //*********************************
    //******* A T R I B U T O S *******
    //*********************************
    private ClientesDAO clientesDAO;

    //***********************************
    //******* C O N S T R U T O R *******
    //***********************************
    public OLDClienteControllerVersao01(ClientesDAO clientes) {
        this.clientesDAO = clientes;
    }

    //*****************************
    //******* M É T O D O S *******
    //*****************************

    //***********************************************
    //************** C O N S U L T A R **************
    //***********************************************
    //@GetMapping("/api/clientes/{codigoCliente}")
    //public ResponseEntity<Cliente> getClienteById( @PathVariable("codigoCliente") Integer id){
    // OU
    @GetMapping("/api/clientes/{id}")
    @ResponseBody // Retorna o corpo da resposta
    public ResponseEntity<Cliente> getClienteById( @PathVariable("id") Integer id){
        Optional<Cliente> cliente = clientesDAO.findById(id); //Pode encontrar o cliente ou não...

        if (cliente.isPresent()) {
            /*
            HttpHeaders headers = new HttpHeaders();
            headers.put("Authorizarion", "token");
            ResponseEntity<Cliente> responseEntity = new ResponseEntity<>(cliente.get(), headers, HttpStatus.OK);
            // EQUIVALE A... ResponseEntity.ok(cliente.get()), conforme abaixo:
            return ResponseEntity.ok(cliente.get()); // Códido de status de retorno = 200; (Retorno com Sucesso) e retorna o CLIE dentro do optional
        }
        return ResponseEntity.notFound().build(); // Códido de status de retorno = 404;
    }

    @GetMapping("/api/clientes")
    @ResponseBody
    public ResponseEntity findCliente(Cliente filtroCliente){
        // Objeto ExampleMatcher que permite identificar um objeto através das suas propriedades
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtroCliente, matcher);
        List<Cliente> listaClientes = clientesDAO.findAll(example);
        return ResponseEntity.ok(listaClientes);

        /*
        String sql = "SELECT * FROM cliente";
        if ( filtroCliente.getNome() != null){
            sql += " WHERE nome = :nome ";
        }

        if ( filtroCliente.getCpf() != null){
            sql += " WHERE cpf = :cpf ";
        }



    }

    //*******************************************
    //************** E X C L U I R **************
    //*******************************************
    @DeleteMapping("/api/clientes/{id}")  //OBS: Essa URL é o endereço de um recurso
    @ResponseBody
    public ResponseEntity<Cliente> excluirCliente( @PathVariable Integer id ){
        Optional<Cliente> cliente = clientesDAO.findById(id);

        if (cliente.isPresent()) {
            clientesDAO.delete(cliente.get());
            return ResponseEntity.noContent().build(); // noContent porque não precisa retornar nada
        }
        return ResponseEntity.notFound().build(); // Códido de status de retorno = 404;
    }

    //*******************************************
    //************** I N C L U I R **************
    //*******************************************
    @PostMapping("/api/clientes") // Rota para chegar até aqui
    @ResponseBody // O QUE vamos retornar: Retorna o corpo da resposta
    public ResponseEntity<Cliente> incluirCliente( @RequestBody Cliente cliente){
        // O cliente é um obj que vai vir no corpo da requisição, que vamos receber no formato JSON. Logo, tenho que mapear o cliente no corpo da requisição
        Cliente clienteSalvo = clientesDAO.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    //*******************************************
    //************ A T U A L I Z A R ************
    //*******************************************
    //@RequestMapping( method = RequestMethod.PUT)
    @PutMapping("/api/clientes/{id}") // URL e Id
    @ResponseBody
    public ResponseEntity atualizarCliente( @PathVariable("id") Integer id,
                                            @RequestBody Cliente clienteNovo ){
        return clientesDAO
                .findById(id) // Busco o cliente existente no banco de dados com esse id
                .map( clienteExistenteBD -> {
                    clienteNovo.setIdCliente( clienteExistenteBD.getIdCliente() );  // Atribuo no Novo Cliente o id do cliente existente no banco de dados
                    clientesDAO.save(clienteNovo); // Atualizo o cliente com os novos dados
                    return ResponseEntity.noContent().build();          // retorno 200 - Sucesso
                })
                .orElseGet( () -> ResponseEntity.notFound().build() );  // retorno 404 - Not Found
    }

}

 */
// ******************************************************************************************************************
    /*
    @RequestMapping(value = "/api/clientes/hello/{nome}", method = RequestMethod.GET) //
    OBS: A anotação @RequestMapping mapeia um EndPoint (Rota/Caminho) através de um verbo HTTP e trata a requisição do Cliente

    *** UMA ROTA(EndPoint) PARA DAR UM HELLO PARA O CLIENTE ***

     URL "/hello/{nome}" pra chegar no método "helloClientes", passando o parâmetro nomeCliente por essa URL
    @RequestMapping(
            value = "/hello/{nome}",
            method = RequestMethod.GET,
            consumes = { "application/json" }
    )
     OBS_1: O que for passado pela URL, pela variável(parâmetro) "nome", vai ser injetado na String nomeCliente
     OBS_2: GET é o "verbo http" que vai acessar esse método "helloClientes". Então, quando eu fizer
     uma requisição GET para a URL "api/clientes/hello/" passando a veriável "nome",vou acessar o método "helloClientes".
    @ResponseBody //OBS: Essa string que vai retornar é o corpo da minha resposta
    public String helloClientes( @PathVariable("nome") String nomeCliente){
        return String.format("Graças a Jeová Deus Todo Poderoso, cheguei até aqui! Olá, %s.", nomeCliente);
    }
     */
