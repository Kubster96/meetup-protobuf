package com.codibly.producer.service;

import com.codibly.model.Measurements;
import com.codibly.producer.model.SerializationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.codibly.utils.JSONMeasurementsGenerator.generateMeasurementsMessages;

@Service
@RequiredArgsConstructor
public class CityService {
    private final MessageQueueService messageQueueService;

    public void sendCityMeasurementsMessages(int numberOfMeasurements, int numberOfMessages, SerializationType serializationType) {
        if (SerializationType.JSON.equals(serializationType)) {
            List<Measurements> measurementsMessages = generateMeasurementsMessages(numberOfMeasurements, numberOfMessages);
            messageQueueService.sendCityMeasurementsMessages(measurementsMessages);
        }
    }
}
