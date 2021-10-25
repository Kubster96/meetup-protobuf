package com.codibly.producer.service;

import com.codibly.model.MeasurementProto;
import com.codibly.model.Measurements;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.codibly.producer.config.RabbitMQProperties.JSON_MEASUREMENT_ROUTING_KEY;
import static com.codibly.producer.config.RabbitMQProperties.PROTOBUF_MEASUREMENT_ROUTING_KEY;

@Service
@RequiredArgsConstructor
public class MessageQueueService {
    private final RabbitTemplate jsonRabbitTemplate;
    private final RabbitTemplate protobufRabbitTemplate;
    private final Exchange exchange;

    public void sendCityJSONMeasurementsMessages(List<Measurements> measurementsMessages) {
        measurementsMessages.forEach(this::sendCityJSONMeasurementsMessage);
    }

    public void sendCityJSONMeasurementsMessage(Measurements measurements) {
        jsonRabbitTemplate.convertAndSend(exchange.getName(),
                JSON_MEASUREMENT_ROUTING_KEY, measurements);
    }

    public void sendCityPROTOBUFMeasurementsMessages(List<MeasurementProto.Measurements> measurementsMessages) {
        measurementsMessages.forEach(this::sendCityPROTOBUFMeasurementsMessage);
    }

    public void sendCityPROTOBUFMeasurementsMessage(MeasurementProto.Measurements measurements) {
        protobufRabbitTemplate.convertAndSend(exchange.getName(),
                PROTOBUF_MEASUREMENT_ROUTING_KEY, measurements);
    }
}
