package com.codibly.consumer.service;

import com.codibly.model.Measurement;
import com.codibly.model.MeasurementProto;
import com.codibly.model.Measurements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeasurementService {
    public void handleJSONMeasurements(List<Measurements> measurementsList) {
        List<Measurement> measurements = measurementsList.stream().flatMap(measurementss ->
                measurementss.getMeasurements().stream()).collect(Collectors.toList());

        int size = measurements.size();
        log.info("{} measurements processed (JSON)", size);
    }

    public void handlePROTOBUFMeasurements(List<MeasurementProto.Measurements> measurementsList) {
        List<MeasurementProto.Measurement> measurements = measurementsList.stream().flatMap(measurementss ->
                measurementss.getMeasurementsList().stream()).collect(Collectors.toList());

        int size = measurements.size();
        log.info("{} measurements processed (PROTOBUF)", size);
    }
}
