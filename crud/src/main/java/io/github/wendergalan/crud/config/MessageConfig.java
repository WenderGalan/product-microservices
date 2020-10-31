package io.github.wendergalan.crud.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Value("${crud.rabbitmq.exchange}")
    private String exchange;

    /**
     *
     */
    @Bean
    public Exchange declareExchange() {
        return ExchangeBuilder
                .directExchange(exchange) // Passa o nome da exchange
                .durable(true) // Configura para que seja durável
                .build();
    }

    /**
     * Faz a conversão das mensagens.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
