package com.codibly.utils;

import com.codibly.model.MeasurementProto;
import com.codibly.model.PollutionProto;
import com.codibly.model.SolarProto;
import com.codibly.model.TrafficProto;
import com.codibly.model.WeatherProto;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class PROTOBUFMeasurementGenerator {
    public static final String DEVICE_ID = "deviceId";
    private static final Map<Integer, Supplier<MeasurementProto.Measurement>> measurementMap;

    static {
        measurementMap = new HashMap<>();
        measurementMap.put(0, PROTOBUFMeasurementGenerator::generatePollutionMeasurement);
        measurementMap.put(1, PROTOBUFMeasurementGenerator::createSolarMeasurement);
        measurementMap.put(2, PROTOBUFMeasurementGenerator::createTrafficMeasurement);
        measurementMap.put(3, PROTOBUFMeasurementGenerator::createWeatherMeasurement);
    }

    private PROTOBUFMeasurementGenerator() {

    }

    private static MeasurementProto.Measurement generatePollutionMeasurement() {
        return MeasurementProto.Measurement.newBuilder()
                .setTimestamp(createProtoTimestamp(Instant.now()))
                .setDeviceId(DEVICE_ID)
                .setPollution(
                        PollutionProto.Pollution.newBuilder()
                                .setCo(10.10)
                                .setNo(11.11)
                                .setPm25(12.12)
                                .build()
                )
                .build();
    }


    private static MeasurementProto.Measurement createSolarMeasurement() {
        return MeasurementProto.Measurement.newBuilder()
                .setTimestamp(createProtoTimestamp(Instant.now()))
                .setDeviceId(DEVICE_ID)
                .setSolar(
                        SolarProto.Solar.newBuilder()
                                .setGhi(10)
                                .setDhi(11)
                                .setDni(12)
                                .build()
                )
                .build();
    }

    private static MeasurementProto.Measurement createTrafficMeasurement() {
        return MeasurementProto.Measurement.newBuilder()
                .setTimestamp(createProtoTimestamp(Instant.now()))
                .setDeviceId(DEVICE_ID)
                .setTraffic(
                        TrafficProto.Traffic.newBuilder()
                                .setRoadNo("roadNo")
                                .setCurrentSpeed(10)
                                .setFreeFlowSpeed(11)
                                .setCurrentTravelTime(12)
                                .setFreeFlowTravelTime(13)
                                .build()
                )
                .build();
    }

    private static MeasurementProto.Measurement createWeatherMeasurement() {
        return MeasurementProto.Measurement.newBuilder()
                .setTimestamp(createProtoTimestamp(Instant.now()))
                .setDeviceId(DEVICE_ID)
                .setWeather(
                        WeatherProto.Weather.newBuilder()
                                .setTemp(10.10)
                                .setPressure(1234)
                                .setHumidity(123)
                                .setWindSpeed(11.11)
                                .setWindDeg(180)
                                .build()
                )
                .build();
    }

    public static List<MeasurementProto.Measurements> generateMeasurementsMessages(int numberOfMeasurements, int numberOfMessages) {
        List<MeasurementProto.Measurements> measurementsList = new ArrayList<>();

        for (int i = 0; i < numberOfMessages; i++) {
            measurementsList.add(generateMeasurementsMessage(numberOfMeasurements));
        }
        return measurementsList;
    }

    private static MeasurementProto.Measurements generateMeasurementsMessage(int numberOfMeasurements) {
        List<MeasurementProto.Measurement> measurements = new ArrayList<>();
        for (int j = 0; j < numberOfMeasurements; j++) {
            measurements.add(createMeasurement(j));
        }
        return MeasurementProto.Measurements.newBuilder()
                .addAllMeasurements(measurements)
                .build();
    }

    private static MeasurementProto.Measurement createMeasurement(int index) {
        return measurementMap.get(index % 4).get();
    }

    private static Timestamp createProtoTimestamp(Instant now) {
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }
}
