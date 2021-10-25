package com.codibly.consumer.config;

import com.codibly.converter.ProtobufMessageConverter;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.codibly.consumer.config.RabbitMQProperties.EXCHANGE;
import static com.codibly.consumer.config.RabbitMQProperties.JSON_MEASUREMENT_BINDING_KEY;
import static com.codibly.consumer.config.RabbitMQProperties.JSON_MEASUREMENT_QUEUE;
import static com.codibly.consumer.config.RabbitMQProperties.PROTOBUF_MEASUREMENT_BINDING_KEY;
import static com.codibly.consumer.config.RabbitMQProperties.PROTOBUF_MEASUREMENT_QUEUE;
import static com.codibly.converter.JsonMessageConverter.createJsonMessageConverter;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue jsonMeasurementQueue() {
        return new Queue(JSON_MEASUREMENT_QUEUE);
    }

    @Bean
    public Binding jsonMeasurementBinding(Queue jsonMeasurementQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(jsonMeasurementQueue)
                .to(topicExchange)
                .with(JSON_MEASUREMENT_BINDING_KEY);
    }

    @Bean
    public Queue protobufMeasurementQueue() {
        return new Queue(PROTOBUF_MEASUREMENT_QUEUE);
    }

    @Bean
    public Binding protobufMeasurementBinding(Queue protobufMeasurementQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(protobufMeasurementQueue)
                .to(topicExchange)
                .with(PROTOBUF_MEASUREMENT_BINDING_KEY);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsonRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        return createContainerFactory(connectionFactory, createJsonMessageConverter());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory protobufRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        return createContainerFactory(connectionFactory, new ProtobufMessageConverter());
    }

    private SimpleRabbitListenerContainerFactory createContainerFactory(ConnectionFactory connectionFactory,
                                                                        MessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);

        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setBatchListener(true);
        factory.setConsumerBatchEnabled(true);
        factory.setConcurrentConsumers(2);
        factory.setBatchSize(100);
        factory.setReceiveTimeout(1000L);

        return factory;
    }
}
