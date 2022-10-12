package io.github.abnobrega.config;

import io.github.abnobrega.security.jwt.JwtAuthFilter;
import io.github.abnobrega.security.jwt.JwtService;
import io.github.abnobrega.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Classe com toda configuração do Spring Security
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //@Configuration
    //public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    //*********************************
    //******* A T R I B U T O S *******
    //*********************************
    @Autowired //******* injetando uma instância da classe UsuarioServiceImpl
    @Lazy //isso faz com que um construtor espere a conclusão do outro, antes de fazer a injeção de dependência.
    private UsuarioServiceImpl usuarioService;

    @Autowired //******* injetando uma instância da classe JwtService
    private JwtService jwtService;

    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************

    /* Configuração do Bean que vai representar o filtro JWT e vai interceptar as requisições. */
    @Bean // Esse Bean é quem vai criptografar e descriptografar as senhas dos usuários
    public PasswordEncoder passwordEncoder(){
        // PasswordEncoder é um pacote do Spring Framework bem avançado de autenticação
        return new BCryptPasswordEncoder(); // Toda vez que usamos o BCrypt ele gera um hash diferente.
    }

    // Criando um Bean para representar o filtro dp JWT, que utilizamos  para interceptar as requisições
    @Bean
    public OncePerRequestFilter jwtFilter(){
        // Retorna o filtro que implementamos passando os parâmetros abaixo:
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    /*  Registrando e configurando o filtro do JWT */
    // Parte que trata da Autenticação de Usuário =
    // Processo de verificação da genuidade da identidade de um objeto ou uma pessoa.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // O AuthenticationManagerBuilder faz a autenticação do usuário e
        // coloca o usuário dentro do contexto do Spring Security
     auth
                .userDetailsService(usuarioService)  // Vai carregar o usuário
                .passwordEncoder(passwordEncoder()); // Vai comparar a senha do usuário

        /*
        Testar o passwordEncoder = Minha configuração do AuthenticationManagerBuilder em memória
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("fulano")
                .password(passwordEncoder().encode("123")) //encriptograda a senha
                .roles("USER", "ADMIN"); // Roles = Perfil de usuário
         */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Parte que trata das Autorizações do Usuário: MAPEAMENTO DOS ENDPOINT (API) x AUTORIZAÇOES
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/clientes/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/pedidos/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/produtos/**")
                        .hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/api/usuarios/**")
                        .permitAll()
                    .anyRequest().authenticated() // Para o caso de eu esquecer alguma url
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    /* A partir de agora, cada requisição vai conter todos os elementos necessários
                        para ela ser verificada: Autenticação e Autorizações.
                        Antes o usuário ficava logado na sessão. Agora, não vamos mais criar sessões, como em httpBasic(),
                        porque as requisições agora são STATLESS. Com isso, nossa aplicação não tem mais sessão de usuário
                        e TODA REQUISIÇÃO PRECISA PASSAR O TOKEN A PARTIR DE AGORA.
                        OBS: Se não passar o token, a aplicação não reconhece o usuário!
                     */
                .and()
                    .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                    // Precisamos colocar o usuário no contexto do Spring Security. Para isso, antes de rodar o filtro UsernamePasswordAuthenticationFilter
                    // POR ISSO, meu filtro jwtFilter é executado antes do filtro "UsernamePasswordAuthenticationFilter" do Spring Security.
                    // OBS; o filtro "UsernamePasswordAuthenticationFilter" do Spring Security faz as seguintes verificações de
                    // segurança da aplicação, como a verificação das autorizações através das Roles.
        ;

        /*.httpBasic();
           httpBasic() = Permite passar requisição http com Header Authorization com nossas credenciais,
           facilitando trabalhar statless (Trabalhar com APIs) e testar via Postman
         */

        //*********************
        //******* ROLES *******
        //*********************
        /* AS ROLES são os perfis dos usuários e funcionam como um "CONTAINER DE AUTORIZAÇÕES"
           ROLE1: Os usuários são vendedores e cadastram clientes (compradores) e fazem pedidos
           ROLE2: Só os administradores poden cadastrar produtos
         */

        // OBS: csrf() => segurança entre appl web e o backend
        //.antMatchers("api/clientes/*").hasRole("USER"); // (Definir quem acessa o que) OU
        //.antMatchers("api/clientes/*").hasAnyAuthority("MANTER USUARIO"); // (Definir quem acessa o que) OU
        //.antMatchers("api/clientes/*").permitAll() // (Definir quem acessa o que)

        /* .formLogin();
        SE for usar: .formLogin("/meu-login.html"), então temos:
        <form method="post">
            <input type="text" name="username">
            <input type="secret" name="password">
            <button type="submit"...
        </form>
         */
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // ignorando as seguintes urls dos recursos carregador pelo swagger, na página Html de documentação,
        // para não passar pelo filtro de segurança, por enquanto, para não fazermos nenhuma configuração.
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }

}
