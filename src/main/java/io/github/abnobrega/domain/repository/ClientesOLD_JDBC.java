package io.github.abnobrega.domain.repository;

import io.github.abnobrega.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// CLASSE DAO que encapsula todas as operações (DML) de acesso à base de dados,
// referentes a uma classe de negócio: Entidade do modelo de negócios.

//***********************************************
// ************** C L I E N TE DAO **************
//***********************************************
@Repository   // Identifica a classe como um repósitório (DAO) da CAMADA de PERSISTÊNCIA
public class ClientesOLD_JDBC {
    private static String SELECT_ALL = "SELECT * FROM cliente";
    //private static String SELECT_POR_NOME = "SELECT * FROM cliente WHERE nome = ?";
    private static String INSERT = "INSERT INTO cliente (nome) VALUES (?)";
    private static String UPDATE = "UPDATE cliente SET nome = ? WHERE id_cliente = ?";
    private static String DELETE = "DELETE FROM cliente WHERE id_cliente = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate; //injetando um objeto jdbcTemplate

    //*********************************
    //******* C O N S U L T A R *******
    //*********************************
    public List <Cliente> obterListaClientes(){
        return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
    }

    private RowMapper<Cliente> obterClienteMapper() {
        return new RowMapper<Cliente>() {
            // O RowMapper mapeia o resultado (ResultSet) de acesso à uma Base de Dados, para dentro de uma classe.
            // No exemplo a seguir, todos os resultados do acesso à BD são mapeados para dentro da classe CLIENTE.
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer idCliente = rs.getInt("id_cliente");
                String nome = rs.getString("nome");
                return new Cliente(idCliente, nome);
                //return new Cliente(rs.getString("nome"));
            }
        };
    }

    public List<Cliente> ConsultarClientePorNome(String nome){
        return jdbcTemplate.query(
                SELECT_ALL.concat(" WHERE nome like ?"),
                new Object[]{"%" + nome + "%"},
                obterClienteMapper());
    }


    //*****************************
    //******* E X C L U I R *******
    //*****************************
    public void excluirCliente(Cliente cliente){
        exluirClientePorId(cliente.getIdCliente());
    }

    public void exluirClientePorId(Integer id){
        jdbcTemplate.update(DELETE, new Object[]{id});
    }


    //*****************************
    //******* I N C L U I R *******
    //*****************************
    public Cliente salvarCliente(Cliente cliente){
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()} );
        return cliente;
    }


    //*****************************
    //******* A L T E R A R *******
    //*****************************
    public Cliente atualizarCliente(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getIdCliente()});
        return cliente;
    }

}
