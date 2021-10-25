package com.codibly.consumer.service;

import com.codibly.model.Measurement;
import com.codibly.model.Measurements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeasurementService {
    public void handleMeasurements(List<Measurements> measurementsList) {
        List<Measurement> measurements = measurementsList.stream().flatMap(measurementss ->
                measurementss.getMeasurements().stream()).collect(Collectors.toList());

        log.info("{} messages processed", measurements.size());
    }
}