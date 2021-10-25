package com.codibly.producer.service;

import com.codibly.model.MeasurementProto;
import com.codibly.model.Measurements;
import com.codibly.producer.model.SerializationType;
import com.codibly.utils.JSONMeasurementsGenerator;
import com.codibly.utils.PROTOBUFMeasurementGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CityService {

    private final MessageQueueService messageQueueService;
    @Value("${producer.serialization-type}")
    private SerializationType serializationType;
    @Value("${producer.run-producer-loop}")
    private boolean runProducerLoop;
    @Value("${producer.number-of-measurements}")
    private int numberOfMeasurements;

    public void sendCityMeasurementsMessages(int numberOfMeasurements, int numberOfMessages,
                                             SerializationType serializationType) {
        if (SerializationType.JSON.equals(serializationType)) {
            List<Measurements> measurementsMessages = JSONMeasurementsGenerator
                    .generateMeasurementsMessages(numberOfMeasurements, numberOfMessages);
            messageQueueService.sendCityJSONMeasurementsMessages(measurementsMessages);
        } else if (SerializationType.PROTOBUF.equals(serializationType)) {
            List<MeasurementProto.Measurements> measurementsMessages = PROTOBUFMeasurementGenerator
                    .generateMeasurementsMessages(numberOfMeasurements, numberOfMessages);
            messageQueueService.sendCityPROTOBUFMeasurementsMessages(measurementsMessages);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void generateAndSendCityMeasurements() {
        while (runProducerLoop) {
            if (SerializationType.JSON.equals(serializationType)) {
                Measurements measurements = JSONMeasurementsGenerator.generateMeasurementsMessage(numberOfMeasurements);
                messageQueueService.sendCityJSONMeasurementsMessage(measurements);
            } else if (SerializationType.PROTOBUF.equals(serializationType)) {
                MeasurementProto.Measurements measurements = PROTOBUFMeasurementGenerator.generateMeasurementsMessage(numberOfMeasurements);
                messageQueueService.sendCityPROTOBUFMeasurementsMessage(measurements);
            }
        }
    }
}
