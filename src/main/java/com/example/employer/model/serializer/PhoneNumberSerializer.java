package com.example.employer.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PhoneNumberSerializer extends JsonSerializer {

    private static final String PREFIX = "+49 ";

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String serializedValue = value == null ? "" : value.toString();
        if (!serializedValue.startsWith(PREFIX)) {
            serializedValue = PREFIX + serializedValue;
        }
        gen.writeString(serializedValue);
    }
}
