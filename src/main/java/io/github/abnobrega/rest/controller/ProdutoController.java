package io.github.abnobrega.rest.controller;

import io.github.abnobrega.domain.entity.Produto;
import io.github.abnobrega.domain.repository.ProdutosDAO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

//import static org.springframework.http.HttpStatus.*

@RestController
@RequestMapping("/api/produtos") // URL base do Controlador Produto
public class ProdutoController {

    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    public ProdutosDAO produtosDAO;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public ProdutoController(ProdutosDAO produtosDAO) {
        this.produtosDAO = produtosDAO;
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************

    //***********************************************
    //************** C O N S U L T A R **************
    //***********************************************
    // *** Consulta Produto por id
    @GetMapping("{id}")
    public Produto getProdutoById( @PathVariable("id") Integer id ){
        return produtosDAO
                .findById(id)
                .orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado.") );
    }

    // *** Pesquisa de Produtos: consulta todos os produtos do BD
    @GetMapping
    public List<Produto> findProduto(Produto filtroProduto){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtroProduto, matcher);
        return produtosDAO.findAll(example);

    }

    //*******************************************
    //************** E X C L U I R **************
    //*******************************************
    @DeleteMapping("{id}") //OBS: Essa URL é o endereço de um recurso
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProduto( @PathVariable("id") Integer id ){
        produtosDAO
                .findById(id)
                .map( produto -> {
                    //produtosDAO.deleteById(id);   // Deleta pelo Id do Objeto
                    produtosDAO.delete(produto);    // Deleta o objeto produto diretamente. (Escolhi esse! Rsrs)
                    return produto;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado.") );
    }

    //*******************************************
    //************** I N C L U I R **************
    //*******************************************
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto inluirProduto( @RequestBody @Valid Produto produto ){
        return produtosDAO.save(produto);
    }

    //*******************************************
    //************ A T U A L I Z A R ************
    //*******************************************
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarProduto( @PathVariable("id") Integer id,
                                  @RequestBody @Valid Produto produtoNovo ) {
        produtosDAO
                .findById(id) // Busco o produto existente com esse id, no banco de dados (BD).
                .map( produtoExistenteBD -> {
                    // Atribuo no Novo Produto com o id do produto existente no BD
                    produtoNovo.setIdProduto( produtoExistenteBD.getIdProduto() );

                    // Atualizo o produto com as novas informações, no BD
                    produtosDAO.save(produtoNovo);

                    return produtoExistenteBD;  // retorno 200 - Sucesso

                })
                .orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado.") );
    }


}
