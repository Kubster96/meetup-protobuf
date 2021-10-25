package com.codibly.consumer.handler;

import com.codibly.consumer.service.MeasurementService;
import com.codibly.model.Measurements;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.codibly.consumer.config.RabbitMQProperties.JSON_MEASUREMENT_QUEUE;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = JSON_MEASUREMENT_QUEUE)
public class JSONMeasurementHandler {
    private final MeasurementService measurementService;

    @RabbitHandler
    public void handleMeasurements(List<Measurements> measurementsList) {
        measurementService.handleMeasurements(measurementsList);
    }
}
