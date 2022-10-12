package io.github.abnobrega.rest.controller;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.abnobrega.domain.entity.ItemPedido;
import io.github.abnobrega.domain.entity.Pedido;
import io.github.abnobrega.domain.enums.StatusPedido;
import io.github.abnobrega.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.abnobrega.rest.dto.InformacoesItemPedidoDTO;
import io.github.abnobrega.rest.dto.InformacoesPedidoDTO;
import io.github.abnobrega.rest.dto.PedidoDTO;
import io.github.abnobrega.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//********************************************
//************** API de PEDIDOS **************
//********************************************
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    // Implementando a API de Pedidos

    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private PedidoService service;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public PedidoController(PedidoService service) {
        this.service = service; // O serviço do Pedido é injetado via construtor
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************

    //***********************************************
    //************** C O N S U L T A R **************
    //***********************************************
    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map( pedidoEncontrado -> converter(pedidoEncontrado))
                .orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){

        InformacoesPedidoDTO informacoesPedidoDTO = new InformacoesPedidoDTO();
        informacoesPedidoDTO.setCodigo(pedido.getIdPedido());
        informacoesPedidoDTO.setDataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        informacoesPedidoDTO.setCpf(pedido.getCliente().getCpf());
        informacoesPedidoDTO.setNomeCliente(pedido.getCliente().getNome());
        informacoesPedidoDTO.setStatusPedido(pedido.getStatusPedido().name());
        informacoesPedidoDTO.setTotalPedido(pedido.getTotalPedido());
        informacoesPedidoDTO.setInformacoesItemPedidoDTO(converterListaItens(pedido.getItens()));

        return informacoesPedidoDTO;

    }

    // Mapeando DE listaItensPedido PARA InformacoesItemPedidoDTO
        private List<InformacoesItemPedidoDTO> converterListaItens(List<ItemPedido> listaItensPedido){
        if (CollectionUtils.isEmpty(listaItensPedido)){
            return Collections.emptyList();
        }

        return listaItensPedido
                .stream()
                .map( itemPedido -> {
                    InformacoesItemPedidoDTO informacoesItemPedidoDTO = new InformacoesItemPedidoDTO();
                    informacoesItemPedidoDTO.setDescricaProduto(itemPedido.getProduto().getDescricao());
                    informacoesItemPedidoDTO.setPrecoUnitario(itemPedido.getProduto().getPrecoUnitario());
                    informacoesItemPedidoDTO.setQuantidade(itemPedido.getQuantidade());

                    return informacoesItemPedidoDTO;

        }).collect(Collectors.toList());
    }

    //*******************************************
    //************** E X C L U I R **************
    //*******************************************

    //*******************************************
    //************** I N C L U I R **************
    //*******************************************
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer incluirPedido(@RequestBody @Valid PedidoDTO pedidoDTO){
        //OBS: Vou receber o arquivo DTO no formato Json
        Pedido pedidoSalvo = service.incluirPedido(pedidoDTO);
        return pedidoSalvo.getIdPedido();
    }

    //*******************************************
    //************ A T U A L I Z A R ************
    //*******************************************
    // @PutMapping => tem que passar todas as informações do pedido, senão ele coloca nulo

    // Seguindo os Padrões RESTFUL
    @PatchMapping("{id}")  // O verbo Patch atualiza apenas os campos informados, recebendo o id do pedido
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna esse estatus
    public void atualizarStatusPedido( @PathVariable("id") Integer id,
                                       @RequestBody @Valid AtualizacaoStatusPedidoDTO atualizacaoStatusPedidoDTO){
        // Obtém o novo status do pedido (novoStatusPedido), no formato string
        String novoStatusPedido = atualizacaoStatusPedidoDTO.getNovoStatus();
        // transforma a string "novoStatusPedido" em um Enum "StatusPedido"
        service.atualizarStatusPedido(id, StatusPedido.valueOf(novoStatusPedido));
        // OBS: Se a string novoStatusPedido for igual ao valor da Enum, ela retorna o valor da Enum.
    }

}
