package io.github.abnobrega.security.jwt;

import io.github.abnobrega.service.impl.UsuarioServiceImpl;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// CLASSE DE FILTRO DO JWT QUE FAZ A AUTENTICAÇÃO DO USUÁRIO
// E COLOCA O USUÁRIO DENTRO DO CONTEXTO DO STRING SECURITY:
// Esta classe faz o papel do AuthenticationManagerBuilder.
// ESSE FILTRO VAI INTERCEPTAR AS REQUISIÇÕES E OBTER O TOKEN DO HEADER AUTHORIZATION.
public class JwtAuthFilter extends OncePerRequestFilter {

    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    private JwtService jwtService;
    private UsuarioServiceImpl usuarioService;

    //*************************************************
    //************** C O N S T R U T O R **************
    //*************************************************
    public JwtAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************
    // OJETIVO GERAL DESSE MÉTODO é INTERCEPTAR A REQUISIÇÃO HTTP, OBTER e VALIDAR o token que foi enviado via
    // requisição e adicionar na requisição um USUÁRIO AUTENTICADO, antes de enviar a requisição pra frente.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // INTERCEPTANDO A REQUISIÇÃO

        /*  OBJETIVOS ESPECÍFICOS DESSE MÉTODO:
            ANTES DE PASSARMOS A REQUISIÇÃO ADIANTE, TEMOS OS SEGUINTES PASSOS:
             (1) Inteceptar a requisição
                1.1) Interceptar o Header da requisição
                1.2) Verificar se o Header é do tipo BEARER (AO PORTADOR)
             (2) Obter as informações da requisição: Obter o token do Header Authorization
             (3) Verificar se o token é válido
             (4) Adicionar um usuário autenticado à requisição:
                4.1) Obter o login do USUÁRIO através do token
                4.2) Instanciar o OBJETO USUÁRIO através do login
                4.3) Implementar um objeto da classe AUTENTICAÇÃO,
                     para COLOCAR O USUÁRIO DENTRO DO CONTEXTO do Spring Security
                4.4) Informar ao Spring Security que se trata de uma autenticação de uma Aplicação Web
                4.5) Obter o CONTEXTO do Spring SecuritY E COLOCAR NO CONTEXTO o objeto de AUTENTICAÇÃO DO USUÁRIO
             (5) Despachar a requisição à frente com o Usuário autenticado
         */

        // PASSO(1): INTERCEPTAR A REQUISIÇÃO

        // 1.1) Interceptar o Header da requisição
        String authorization = request.getHeader("Authorization");

        /* O Tojen Jwt tem o Formato Padrão Beared Token => KEY: Authorization / VALUE: Beared <<código do token>>) */
        // 1.2) Verificar se o Header é do tipo Beared
        if (authorization != null && authorization.startsWith("Bearer")) {

            // PASSO(2): OBTER AS INFORMAÇÕES DA REQUISIÇÃO:
            // Para tanto, OBTER O TOKEN do HEADER AUTHORIZATION, QUE FOI ENVIADO VIA REQUISIÇÃO
            String token = authorization.split(" ")[1];
            /*  OBS: O método Split transforma a string do Header Authorization (KEY / VALUE)
                em um array e pega a posição 1 do array, que contém o código do Token.
                String => Beared <<código do token>> em array[0,1] = [Beared <<código do token>>]
            */

            // 3) VERIFICAR SE O TOKEN ESTÁ VÁLIDO
            boolean isValid = jwtService.tokenValido(token);

            if (isValid){
                // PASSO(4): ADICIONAR UM USUÁRIO AUTENTICADO À REQUISIÇÃO

                // 4.1) OBTER O LOGIN DO USUÁRIO através do token
                String loginUsuario = jwtService.obterLoginUsuario(token);

                // 4.2) INSTANCIAR O OBJETO USUÁRIO através do login
                UserDetails userDetails = usuarioService.loadUserByUsername(loginUsuario);

                // 4.3) IMPLEMENTAR UM OBJETO DA CLASSE DE AUTENTICAÇÃO: UsernamePasswordAuthenticationToken,
                // PARA COLOCA O USUÁRIO DENTRO DO CONTEXTO do Spring Security
                UsernamePasswordAuthenticationToken userAuthentication = new
                        UsernamePasswordAuthenticationToken(
                            userDetails,                    // Usuário (USUA)
                            null,                           // Credenciais nulas
                            userDetails.getAuthorities()    // Autorizações do USUA
                );

                // 4.4) INFORMAMOS AO SPRING SECURITY QUE SE TRATA DE UMA AUTENTICAÇÃO DE UMA APLICAÇÃO WEB
                userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4.5) OBTER O CONTEXTO do Spring Security
                // e COLOCAR no CONTEXTO O OBJETO DE AUTENTICAÇÃO DO USUÁRIO (userAuthentication)
                // pora dentro do SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(userAuthentication);

                /* ******* Neste momento o usuário já está dentro do contexto do Spring Security ******* */

            }

        }

        // PASSO(5): DESPACHAR A REQUISIÇÃO À FRENTE COM O USUÁRIO AUTENTICADO

        // Com o filtro implementado, despachamos a requição adiante.
        filterChain.doFilter(request, response);
        /* Dentro do DoFilter ele vai chamar a configuração das autorizações do usuário,
            implementadas no método: SecurityConfig.configure(HttpSecurity http)
         */

        /* IMPORTANTE:
            Precisamos agora registrar essa classe: JwtAuthFilter, dentro do Spring Security,
            para ela interceptar e tratar as requisições antes de chegarem no Spring Security,
            conforme a lógica implementada acima.
         */

    }
}













