package com.codibly.producer.service;

import com.codibly.model.Measurements;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.codibly.producer.config.RabbitMQProperties.JSON_MEASUREMENT_ROUTING_KEY;

@Service
@RequiredArgsConstructor
public class MessageQueueService {
    private final RabbitTemplate jsonRabbitTemplate;
    private final Exchange exchange;

    public void sendCityMeasurementsMessages(List<Measurements> measurementsLists) {
        measurementsLists.forEach(measurements ->
                jsonRabbitTemplate.convertAndSend(exchange.getName(), JSON_MEASUREMENT_ROUTING_KEY, measurements));
    }
}
