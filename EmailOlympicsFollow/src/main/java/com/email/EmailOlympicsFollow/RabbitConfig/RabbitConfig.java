package com.email.EmailOlympicsFollow.RabbitConfig;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.queue}")
    private String QUEUE_NAME;

    @Bean
    @Operation(
            summary = "Criação da fila",
            description = "Realiza a criação da fila não durável com nome injetado na variável QUEUE_NAME."
    )
    public Queue createQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    @Operation(
            summary = "Criação da rabbit admin",
            description = "Cria o objeto rabbitAdmin para que possam ser realizadas operações adminitstrativas (funções de admin)."
    )
    public RabbitAdmin generateRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    @Operation(
            summary = "Inicialização da rabbit admin",
            description = "Inicializa o objeto rabbitAdmin. O evento ApplicationReadyEvent é utilizado para garantir que a aplicação esteja pronta para receber requisições."
    )
    public ApplicationListener<ApplicationReadyEvent> startAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    @Operation(
            summary = "Criação do rabbit template",
            description = "Cria o objeto rabbitTemplate para que possam ser realizadas operações de envio de mensagens."
    )
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    @Operation(
            summary = "Criação do Jackson2MessageConverter",
            description = "Cria o objeto messageConverter para que possam ser realizadas operações de conversão de mensagens."
    )
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
