package com.codibly.producer.service;

import com.codibly.model.MeasurementProto;
import com.codibly.model.Measurements;
import com.codibly.producer.model.SerializationType;
import com.codibly.utils.JSONMeasurementsGenerator;
import com.codibly.utils.PROTOBUFMeasurementGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CityService {
    private final MessageQueueService messageQueueService;

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
}
