package com.codibly.utils;

import com.codibly.model.Measurement;
import com.codibly.model.Measurements;
import com.codibly.model.Pollution;
import com.codibly.model.Solar;
import com.codibly.model.Traffic;
import com.codibly.model.Weather;
import com.codibly.model.WindSpeedUnit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JSONMeasurementsGenerator {
    public static final String DEVICE_ID = "deviceId";
    private static final Map<Integer, Supplier<Measurement>> measurementMap;

    static {
        measurementMap = new HashMap<>();
        measurementMap.put(0, JSONMeasurementsGenerator::generatePollutionMeasurement);
        measurementMap.put(1, JSONMeasurementsGenerator::createSolarMeasurement);
        measurementMap.put(2, JSONMeasurementsGenerator::createTrafficMeasurement);
        measurementMap.put(3, JSONMeasurementsGenerator::createWeatherMeasurement);
    }

    private JSONMeasurementsGenerator() {

    }

    private static Pollution generatePollutionMeasurement() {
        return Pollution.builder()
                .timestamp(Instant.now())
                .deviceId(DEVICE_ID)
                .co(10.10)
                .no(11.11)
                .pm25(12.12)
                .build();
    }

    private static Solar createSolarMeasurement() {
        return Solar.builder()
                .timestamp(Instant.now())
                .deviceId(DEVICE_ID)
                .ghi(10)
                .dhi(11)
                .dni(12)
                .build();
    }

    private static Traffic createTrafficMeasurement() {
        return Traffic.builder()
                .timestamp(Instant.now())
                .deviceId(DEVICE_ID)
                .roadNo("roadNo")
                .currentSpeed(10)
                .freeFlowSpeed(11)
                .currentTravelTime(11)
                .freeFlowTravelTime(13)
                .build();
    }

    private static Weather createWeatherMeasurement() {
        return Weather.builder()
                .timestamp(Instant.now())
                .deviceId(DEVICE_ID)
                .temp(10.10)
                .pressure(1234)
                .humidity(123)
                .windSpeed(11.11)
                .windSpeedUnit(WindSpeedUnit.METER_PER_SECOND)
                .windDeg(180)
                .build();
    }

    public static List<Measurements> generateMeasurementsMessages(int numberOfMeasurements, int numberOfMessages) {
        List<Measurements> measurementsList = new ArrayList<>();

        for (int i = 0; i < numberOfMessages; i++) {
            measurementsList.add(generateMeasurementsMessage(numberOfMeasurements));
        }
        return measurementsList;
    }

    private static Measurements generateMeasurementsMessage(int numberOfMeasurements) {
        List<Measurement> measurements = new ArrayList<>();
        for (int j = 0; j < numberOfMeasurements; j++) {
            measurements.add(createMeasurement(j));
        }
        return Measurements.builder()
                .measurements(measurements)
                .build();
    }

    private static Measurement createMeasurement(int index) {
        return measurementMap.get(index % 4).get();
    }
}
