package io.github.abnobrega.security.jwt;

import io.github.abnobrega.VendasApplication;
import io.github.abnobrega.domain.entity.Usuario;
import io.github.abnobrega.domain.repository.UsuarioDAO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service // Para ela fazer parte do IoC do Spring.
public class JwtService {
    /*  CLASSE PARA GERAR (CODIFICAR) E DECODIFICAR O TOKEN.
        Usada sempre que precisarmos saber QUEM é o usuário e  verificarmos se o token está valido.
     */
    //*************************************************
    //**************  A T R I B U T O S  **************
    //*************************************************
    @Value("${security.jwt.expiracao-token}") // Injetando o período de expiraçao do token
    //@NotEmpty(message = "{campo.senha.obrigatorio}")
    private String expiracaoToken;  // definir o período de expiraçao do meu token: Ex 30 minutos

    @Value("${security.jwt.chave-assinatura}") // Injetando a chave de assinatura do token
    private String chaveAssinatura; // Chave-privada: Só essa classe vai conhecer para gerar e decodificar o token


    //*************************************************
    //************** M  É  T  O  D  O  S **************
    //*************************************************

    // MÉTODO PARA GERAR O TOKEN
    public String gerarToken(Usuario usuario){
        // converter expiração: De String Para Long
        long expLong = Long.valueOf(expiracaoToken);

        // Instancia objeto com data/hora atual + o tempo em minutos, definidos para a DATA DE Expiração do token
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expLong);

        // Converte o objeto dataHoraExpiracao em um objeto do tipo Instant, com a zona default do Sist. Operacional
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();

        // Converte o objeto Instant em um objeto do tipo Date para seu usado pelo JWT (Json Web Token)
        Date data = Date.from(instant);

        // Os claims significam as informações dos tokens. Para tanto, temos:
        //HashMap<String, Object> claims = new HashMap<>();
        //claims.put("emaildousuario", "usuario@gmail.com");
        //claims.put("roles", "admin" );

        // Criar e retorna uma string com o token JWT (Json Web Token)
        return Jwts
                .builder()
                .setSubject(usuario.getLogin()) // Uma identificação para saber quem é o usuário que fez a requisição
                .setExpiration(data)            // A data de expiração deinida
                //.setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura) // Algoritmo e chave de assinatura
                .compact(); // vai compactar tudo em uma string que será retornada (O JWT)
    }

    // MÉTODO PRIVADO QUE VAI RETORNAR OS CLAIMS (AS INFORMAÇÕES) DO TOKEN
    private Claims obterClaimsToken( String token ) throws ExpiredJwtException {
        return Jwts
                .parser()                       // Método para decodificar o token
                .setSigningKey(chaveAssinatura) // Informa a chave de assinatura do token
                .parseClaimsJws(token)          // Passo o token que será decodificado
                .getBody();                     // Método para retornar os claims (as informações) do token
    }

    // MÉTODO PARA PEGAR O TOKEN E VERIFICAR A VALIDADE DO TOKEN
    public boolean tokenValido( String token ) {
        try {
            // Obtem as informaçoes do token
            Claims claims = obterClaimsToken(token);

            // Extrai a data de expiração do token
            Date dataExpiracaoToken = claims.getExpiration();

            /* Verificar se a data/hora atual do sistema já ultrapassou a data/hora de expiração */
            // Para tanto, transforma o objeto do tipo Date (dataExpiracaoToken) em um objeto do tipo LocalDateTimeToken.
            LocalDateTime localDateTimeToken = dataExpiracaoToken.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            // O token é valido quando a DataHora atual não é superior á data de expiração do token
            return (!LocalDateTime.now().isAfter(localDateTimeToken));

        } catch (Exception exception ) {
            return false;
        }

    }

    // MÉTODO PARA SABER QUEM É O USUÁRIO QUE ENVIOU O TOKEN, QUE É QUEM VAI ESTAR LOGADO.
    public String obterLoginUsuario( String token ) throws ExpiredJwtException {
        // Identifica quem é o usuário e seu respectivo login [getSubject()]
        return (String) obterClaimsToken(token).getSubject();
    }

    // Método para rodar a aplicação em modo Stand Alone

    /*
    public static void main(String[] args) {
        // instanciando um contexto de injeção do Spring para iniciar a aplicação
        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
        JwtService jwtService = contexto.getBean(JwtService.class);


        // Gerando um tokem de teste
        //Usuario usuario = new Usuario();
        //usuario.setLogin("fulano");

        //String token = jwtService.gerarToken(usuario);
        //System.out.println(token);

        // Verificar se o token é valido
        //boolean isTokenValido = jwtService.tokenValido(token);
        //System.out.println("O Token está válido?" + isTokenValido);

        //System.out.println(jwtService.obterLoginUsuario(token));

    }

     */

}
