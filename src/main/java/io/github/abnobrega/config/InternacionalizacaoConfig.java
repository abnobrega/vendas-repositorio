package io.github.abnobrega.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternacionalizacaoConfig {

    //*****************************
    //******* M É T O D O S *******
    //*****************************

    // Através do método messageSource() abaixo, vou definir a fonte de mensagen de erro do meuSistema.
    // messages.properties (PORTUGUES) ou messages_en.properties (INGLÊS)
    @Bean
    public MessageSource messageSource(){
        // Instanciando um objeto messageSource, da classe "Fonte de Mensagem" (MessageSource)
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // referência ao arquivo resources: messages.properties
        messageSource.setDefaultEncoding("ISO-8859-1");
        messageSource.setDefaultLocale(Locale.getDefault()); // Obtem o local padrão de onde está rodando a aplicação
        return messageSource;
    }

    // LocalValidatorFactoryBean é o objeto responável por fazer a interpolação das mensagens no messageSource:
    // Ele é quem vai trocar o campo {nome do campo} por sua respectiva mensagem do message.properties
    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }


}
