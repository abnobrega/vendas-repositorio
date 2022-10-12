package io.github.abnobrega.service.impl;

import io.github.abnobrega.domain.entity.Usuario;
import io.github.abnobrega.domain.exception.SenhaInvalidaException;
import io.github.abnobrega.domain.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

// Classe para carregar o usuário da base de dados, através de seu login (username).
@Service
public class UsuarioServiceImpl implements UserDetailsService {

    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    @Autowired // Injetando o PasswordEncoder
    private PasswordEncoder encoder;
    // Uma instãncia do PasswordEncoder foi Injetado para fazer a criptografia da senha

    @Autowired // Injetando a Interface UsuarioDAO, para carregar o usuário pelo username
    private UsuarioDAO usuarioDAO;

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************

    //***********************************************
    //************* A U T E N T I C A R *************
    //***********************************************
    public UserDetails autenticar(Usuario usuarioRequisicao) {
        // Obter o usuário cadastastrado no BD
        UserDetails usuarioCadastradoBD = loadUserByUsername(usuarioRequisicao.getLogin());

        // Verificar se a senha do usuário informada na requisição, confere com a senha cadastrada no BD.
        boolean senhasConferem = encoder.matches(usuarioRequisicao.getSenha(), usuarioCadastradoBD.getPassword());
        if (senhasConferem){
            return usuarioCadastradoBD;
        }
        throw new SenhaInvalidaException();
    }

    //***********************************************
    //************** C O N S U L T A R **************
    //***********************************************
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Obtém o usuário pelo login
        Usuario usuario = usuarioDAO.findByLogin(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado."));

        /*  retorna um array do tipo string de roles
            SE (o usua for administardor) ENTÃO
                retorna um array de strings com a roles: ADMIN e USER
            SENÃO
                retorna um array de string apenas com a role USER.
            FIM-SE
         */
        String[] roles = usuario.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        // Retornando um UserDetails para o chamador
        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())  // A senha já vem criptografada da BD.
                .roles(roles)
                .build();
                // Agora vou criar um endpoint para cadastrar usuario

        /*  VERSÃO ANTERIOR
            if(!username.equals("ciclano")){
                throw new UsernameNotFoundException("Usuário não encontrado");
            }

            // Retorna uma instãncia de UserDetail
            return User
                    .builder()
                    .username("ciclano")
                    .password(encoder.encode("123"))
                    .roles("USER", "ADMIN") // Uma String para cada role
                    .build();
            // Isso aqui é como se estivesse simulando um usuário carregado da BD.
        */
    }

    //*******************************************
    //************** E X C L U I R **************
    //*******************************************

    //*******************************************
    //************** I N C L U I R **************
    //*******************************************
    @Transactional // Anotação que garante o commit (no sucesso) ou o rollback (no erro)
    public Usuario incluirUsuario(Usuario usuario){
        return usuarioDAO.save(usuario);

    }

    //*******************************************
    //************ A T U A L I Z A R ************
    //*******************************************

}
