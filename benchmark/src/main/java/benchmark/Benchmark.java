package benchmark;

import com.codibly.model.MeasurementProto;
import com.codibly.model.Measurements;
import com.codibly.utils.JSONMeasurementsGenerator;
import com.codibly.utils.PROTOBUFMeasurementGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;

public class Benchmark {

    public static void main(String[] args) throws IOException {
        int numberOfMeasurements = 100000;
        int numberOfRepeats = 1;

        System.out.println("PROTOBUF - BENCHMARK - ####################");
        double sumProtobuf = 0;
        for (int i = 0; i < numberOfRepeats; i++) {
            sumProtobuf += protobufBenchmark(numberOfMeasurements);
        }
        System.out.println("PROTOBUF - AVERAGE - " + sumProtobuf / numberOfRepeats);


        System.out.println("JSON - BENCHMARK - ########################");
        long sumJson = 0;
        for (int i = 0; i < numberOfRepeats; i++) {
            sumJson += jsonBenchmark(numberOfMeasurements);
        }
        System.out.println("JSON - AVERAGE - " + sumJson / numberOfRepeats);

    }

    private static long protobufBenchmark(int numberOfMeasurements) throws InvalidProtocolBufferException {
        long startTime = System.currentTimeMillis();
        MeasurementProto.Measurements measurements = PROTOBUFMeasurementGenerator
                .generateMeasurementsMessage(numberOfMeasurements);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.printf("PROTOBUF - It took %d milliseconds to generate %d measurements%n", estimatedTime, numberOfMeasurements);

        long processStartTime = System.currentTimeMillis();

        startTime = System.currentTimeMillis();
        byte[] bytes = measurements.toByteArray();
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.printf("PROTOBUF - It took %d milliseconds to serialize %d measurements and size is %d bytes%n", estimatedTime, numberOfMeasurements, bytes.length);

        startTime = System.currentTimeMillis();
        MeasurementProto.Measurements deserialized = MeasurementProto.Measurements.parseFrom(bytes);
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.printf("PROTOBUF - It took %d milliseconds to deserialize %d measurements%n", estimatedTime, numberOfMeasurements);

        long processEstimatedTime = System.currentTimeMillis() - processStartTime;
        System.out.printf("PROTOBUF - It took %d milliseconds to process %d measurements%n", processEstimatedTime, numberOfMeasurements);
        return processEstimatedTime;
    }

    private static long jsonBenchmark(int numberOfMeasurements) throws IOException {
        var objectMapper = createObjectMapper();

        long startTime = System.currentTimeMillis();
        Measurements measurements = JSONMeasurementsGenerator
                .generateMeasurementsMessage(numberOfMeasurements);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.printf("JSON - It took %d milliseconds to generate %d measurements%n", estimatedTime, numberOfMeasurements);

        long processStartTime = System.currentTimeMillis();

        startTime = System.currentTimeMillis();
        byte[] bytes = objectMapper.writeValueAsBytes(measurements);
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.printf("JSON - It took %d milliseconds to serialize %d measurements and size is %d bytes%n", estimatedTime, numberOfMeasurements, bytes.length);

        startTime = System.currentTimeMillis();
        Measurements deserialized = objectMapper.readValue(bytes, Measurements.class);
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.printf("JSON - It took %d milliseconds to deserialize %d measurements%n", estimatedTime, numberOfMeasurements);

        long processEstimatedTime = System.currentTimeMillis() - processStartTime;
        System.out.printf("JSON - It took %d milliseconds to process %d measurements%n", processEstimatedTime, numberOfMeasurements);
        return processEstimatedTime;
    }

    private static ObjectMapper createObjectMapper() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.codibly.model")
                .allowIfSubType("java.util.ArrayList")
                .build();

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        mapper.registerModule(new JavaTimeModule());
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

        return mapper;
    }
}
