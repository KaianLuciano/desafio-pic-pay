package com.picpaychallenge.common.domain.config.exception.messageerror;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MessageErrorSerializer extends JsonSerializer<MessageError> {

    @Override
    public void serialize(MessageError messageError, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("status", messageError.getStatus());
        gen.writeStringField("code", messageError.getCode());
        gen.writeStringField("message", messageError.getMessage());
        gen.writeEndObject();
    }
}
