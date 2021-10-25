package com.codibly.consumer.config;

public class RabbitMQProperties {

    public static final String JSON_MEASUREMENT_QUEUE = "json.measurement";
    public static final String PROTOBUF_MEASUREMENT_QUEUE = "protobuf.measurement";
    public static final String JSON_MEASUREMENT_BINDING_KEY = "json.measurement.*";
    public static final String PROTOBUF_MEASUREMENT_BINDING_KEY = "protobuf.measurement.*";
    public static final String EXCHANGE = "measurement.exchange";

    private RabbitMQProperties() {
    }
}
