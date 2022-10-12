package io.github.abnobrega.domain.repository;

import io.github.abnobrega.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

// CLASSE DAO que encapsula todas as operações (DML) de acesso à base de dados,
// referentes a uma classe de negócio: Entidade do modelo de negócios.

//***********************************************
// ************** C L I E N TE DAO **************
//***********************************************

@Repository   // Identifica a classe como um repósitório (DAO) da CAMADA de PERSISTÊNCIA
public class ClientesDAO_JPAold {
    // EntityManager é a interface que faz todas as operações na Base de Dados, com as Entidades
    @Autowired
    private EntityManager entityManager;

    //*********************************
    //******* C O N S U L T A R *******
    //*********************************
    @Transactional(readOnly = true)
    public List <Cliente> obterListaClientes(){
        return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> ConsultarClientePorNome(String nome){
        //String jpql = "SELECT c.id, c.nome FROM Cliente c" +
        String jpql = "SELECT c FROM Cliente c WHERE c.nome like :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }


    //*****************************
    //******* E X C L U I R *******
    //*****************************
    @Transactional
    public void exluirClientePorId(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        excluirCliente(cliente);
    }

    @Transactional
    public void excluirCliente(Cliente cliente){
        if(!entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    //*****************************
    //******* I N C L U I R *******
    //*****************************
    @Transactional
    public Cliente salvarCliente(Cliente cliente){
        entityManager.persist(cliente);
        return cliente;
    }


    //*****************************
    //******* A L T E R A R *******
    //*****************************
    @Transactional
    public Cliente atualizarCliente(Cliente cliente){
        entityManager.merge(cliente);
        return cliente;
    }

}
