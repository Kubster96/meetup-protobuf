package com.codibly.converter;

import com.codibly.model.MeasurementProto;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class ProtobufMessageConverter implements MessageConverter {
    private static final Map<String, MessageLite.Builder> builderMap;
    private static final String PROTOBUF_CONTENT_TYPE = "application/protobuf";

    static {
        builderMap = new HashMap<>();
        builderMap.put(MeasurementProto.Measurements.class.getSimpleName(), MeasurementProto.Measurements.newBuilder());
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        String messageType = object.getClass().getSimpleName();
        if (!builderMap.containsKey(messageType)) {
            throw new MessageConversionException(format("Type [%s] is not supported", messageType));
        }
        var messageLite = (MessageLite) object;
        byte[] byteArray = messageLite.toByteArray();

        messageProperties.setContentType(PROTOBUF_CONTENT_TYPE);
        messageProperties.setContentLength(byteArray.length);
        messageProperties.setType(messageType);
        messageProperties.setTimestamp(new Date());

        return new Message(byteArray, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        var messageProperties = message.getMessageProperties();
        String contentType = messageProperties.getContentType();
        if (!PROTOBUF_CONTENT_TYPE.equals(contentType)) {
            throw new AmqpRejectAndDontRequeueException(format("Incorrect content-type [%s]", contentType));
        }
        String messageType = messageProperties.getType();
        if (!builderMap.containsKey(messageType)) {
            throw new AmqpRejectAndDontRequeueException(format("Type [%s] is not supported", messageType));
        }
        try {
            var builder = builderMap.get(messageType).clone().clear();
            return builder.mergeFrom(message.getBody()).build();
        } catch (InvalidProtocolBufferException e) {
            throw new AmqpRejectAndDontRequeueException("Cannot convert message", e);
        }
    }
}
