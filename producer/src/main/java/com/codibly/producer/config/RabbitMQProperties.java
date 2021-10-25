package com.codibly.producer.config;

public class RabbitMQProperties {

    public static final String JSON_MEASUREMENT_ROUTING_KEY = "json.measurement.content";
    public static final String PROTOBUF_MEASUREMENT_ROUTING_KEY = "protobuf.measurement.content";
    public static final String EXCHANGE = "measurement.exchange";

    private RabbitMQProperties() {
    }
}
