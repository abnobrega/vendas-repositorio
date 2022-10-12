package io.github.abnobrega.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // Criar uma instância de Docket necessária para o Swagger: que é a configuração do Swagger
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                // As APIs estão no pacote rest=>controller e serão scaneadas pelo swagger
                .apis(RequestHandlerSelectors
                        .basePackage("io.github.abnobrega.rest.controller"))
                .paths(PathSelectors.any())
                .build() // Chama o método build para gerar o objeto docket
                // adcicionar esses dois obj security depois do build
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo()); // Para colcar as informações na API
    }
    /* Esses objetos serão passados para o Docket e, consequentemente, para o Swagger  */

    // Criar uma instância de ApiInfo que vai para dentro do Docket.
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Vendas API")
                .description("API do projeto de vendas")
                .version("1.0")
                .contact(contact())
                .build(); // Método que constroi o objeto.
    }

    private Contact contact(){
        // Dados de contato do desenvolvedor: nome, URL do website e email.
        // Esses dados vão aparecer na documemntação da API via Swagger
        return new Contact(
                "Alexandre Bonturi Nóbrega",
                "http://github.com/abnobrega",
                "abonturi@gmail.com"
                );
    }

    // Criar uma ApiKey retornando os parãmetros: "JWT", "Authorization" e "header"
    public ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

    // Obter uma lista de Security Reference para definir o escopo de autorização
    private List<SecurityReference> defaultAuth(){
        // Define o escopo da autorização
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global",
                "accessEverything");

        // Criar um array de Escopos de Autorização, com 1 posição, com o escopo definido acima
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;

        // Criar uma referência, do escopo para com a ApiKey, por meio do nome: "JWT"
        SecurityReference reference = new SecurityReference("JWT", scopes);

        // Criar uma lista de Security References e adicionar a referência acima
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(reference);

        // Retornar a lista de referência com o escopo de autorização
        return auths;
    }

    private SecurityContext securityContext(){

        return SecurityContext
                .builder()
                .securityReferences(defaultAuth()) // referências de segurança
                .forPaths(PathSelectors.any()) // Para todosos paths escaneados pelo Swagger
                .build();
    }

}
