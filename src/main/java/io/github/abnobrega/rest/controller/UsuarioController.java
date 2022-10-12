package io.github.abnobrega.rest.controller;

import io.github.abnobrega.domain.entity.Usuario;
import io.github.abnobrega.domain.exception.SenhaInvalidaException;
import io.github.abnobrega.domain.repository.UsuarioDAO;
import io.github.abnobrega.rest.dto.CredenciaisDTO;
import io.github.abnobrega.rest.dto.TokenDTO;
import io.github.abnobrega.security.jwt.JwtService;
import io.github.abnobrega.service.impl.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

//****************************************************
//*******  UsuarioController = API de USUÁRIO  *******
//****************************************************
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private final UsuarioServiceImpl usuarioService;// Injeta o UsuarioServiceImpl
    private final PasswordEncoder passwordEncoder;  // Injeta o PasswordEncoder
    private final JwtService jwtService; // Injeta o jwtService para gerar um Token

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public UsuarioController(UsuarioServiceImpl usuarioService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    // Implementar o EndPoint (nossa rota/caminho) que vai requisitar a autenticação e
    // retornar o token do usuário

    //*******************************************
    //*********** A U T E N T I C A R ***********
    //*******************************************
    /* Método para autenticar o usuário e para a geração do token do usuário */
    // mais seguro para enviar as informações, principalmente as credenciais do Usuário
    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try {
            Usuario usuario = new Usuario();
            usuario.setLogin(credenciais.getLogin());
            usuario.setSenha(credenciais.getSenha());
            // Validando se o usuário está autenticado
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            // Gerar o Token
            String token = jwtService.gerarToken(usuario);
            // Retornar o Token
            return new TokenDTO(usuario.getLogin(),token);

        }catch (UsernameNotFoundException | SenhaInvalidaException exception){
            // Não encontrou o usuário, então, retorna a mensagem: "usuário não autorizado."
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
        }
    }


    //***********************************************
    //************** C O N S U L T A R **************
    //***********************************************

    //*******************************************
    //************** E X C L U I R **************
    //*******************************************

    //*******************************************
    //************** I N C L U I R **************
    //*******************************************
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // Endpoint que recebe um @RequestBody com os dados do usuário para cadastrá-lo na BD
    public Usuario incluirUsuario(@RequestBody @Valid Usuario usuario){
        // Criptografando a senha do usuário
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.incluirUsuario(usuario);

    }

    //*******************************************
    //************ A T U A L I Z A R ************
    //*******************************************
}
