package com.codibly.producer.config;

import com.codibly.converter.ProtobufMessageConverter;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.codibly.converter.JsonMessageConverter.createJsonMessageConverter;
import static com.codibly.producer.config.RabbitMQProperties.EXCHANGE;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(createJsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate protobufRabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new ProtobufMessageConverter());
        return rabbitTemplate;
    }
}
