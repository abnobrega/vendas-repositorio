package io.github.abnobrega;

import io.github.abnobrega.domain.entity.Cliente;
import io.github.abnobrega.domain.entity.Pedido;
import io.github.abnobrega.domain.repository.ClientesDAO;
import io.github.abnobrega.domain.repository.PedidosDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;


import io.github.abnobrega.domain.entity.Cliente;
import io.github.abnobrega.domain.repository.ClientesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/*
import org.springframework.context.annotation.Bean;

import javax.persistence.SecondaryTable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;
 */

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {

    // SpringBootServletInitializer é a classe que vai inicializar a Aplicação

    // Criar um Bean de teste, intejando o repositório de Clientes(DAO Clientes), para testar o metodo salvar cliente (INCLUIR)

    //@Bean
    //public CommandLineRunner init(
    //        @Autowired ClientesDAO clientesDAO,
    //        @Autowired PedidosDAO pedidosDAO
    //) {
    //    return args -> {
            /*
            System.out.println(" ******* Criando clientes *******");

            clientesDAO.save(new Cliente("Alexandre Bonturi Nóbrega"));
            clientesDAO.save(new Cliente("Karla Xavier de Pádua Bonturi Nóbrega"));
            clientesDAO.save(new Cliente("Suely Bonturi Nóbrega"));
            //clientesDAO.save(new Cliente("Cliente 4 de teste"));
            //clientesDAO.save(new Cliente("Cliente 5 de teste"));

            Cliente fulano = new Cliente("Fulano");
            clientesDAO.save(fulano);

            System.out.println("******* Consultando todos os clientes *******");
            List<Cliente> todosClientes = clientesDAO.findAll();
            todosClientes.forEach(System.out::println); // método de reference (java8)

            Pedido p = new Pedido();
            p.setCliente(fulano);
            p.setDataPedido(LocalDate.now());
            p.setTotalPedido(BigDecimal.valueOf(77.73));
            pedidosDAO.save(p);

            System.out.println("******* Buscando o cliente Fulano *******");
            Cliente clienteFulano = clientesDAO.findClienteFetchPedidos(fulano.getIdCliente());
            System.out.println(clienteFulano);

            System.out.println("******* Consultando os pedidos do cliente Fulano *******");
            System.out.println(clienteFulano.getPedidos());

            System.out.println("******* Buscando os pedidos do cliente Fulano *******");
            pedidosDAO.findByCliente(fulano).forEach(System.out::println);

             */
       // };
    //}


    //**********************************************
    //******* DESENVOLVENDO UMA API RESTFULL *******
    //**********************************************

    // Inserir um cliente no banco de dados
    /*
    @Bean
    public CommandLineRunner commandLineRunner(@Autowired ClientesDAO clientesDAO){
        return args -> {
            Cliente c = new Cliente( null, "Fulaninho+");
            clientesDAO.save(c);
        };
    }

     */


    public static void main(String[] args){
        SpringApplication.run(VendasApplication.class, args);
    }

}
