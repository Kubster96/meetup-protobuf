package com.codibly.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

public class JsonMessageConverter {

    private JsonMessageConverter() {
    }

    public static MessageConverter createJsonMessageConverter() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.codibly.model")
                .allowIfSubType("java.util.ArrayList")
                .build();

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        mapper.registerModule(new JavaTimeModule());
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonMessageConverter(mapper);
    }
}
