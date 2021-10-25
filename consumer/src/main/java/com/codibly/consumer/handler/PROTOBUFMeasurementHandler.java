package com.codibly.consumer.handler;

import com.codibly.consumer.service.MeasurementService;
import com.codibly.model.MeasurementProto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.codibly.consumer.config.RabbitMQProperties.PROTOBUF_MEASUREMENT_QUEUE;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = PROTOBUF_MEASUREMENT_QUEUE, containerFactory = "protobufRabbitListenerContainerFactory")
public class PROTOBUFMeasurementHandler {
    private final MeasurementService measurementService;

    @RabbitHandler
    public void handleMeasurements(List<MeasurementProto.Measurements> measurementsList) {
        measurementService.handlePROTOBUFMeasurements(measurementsList);
    }
}
