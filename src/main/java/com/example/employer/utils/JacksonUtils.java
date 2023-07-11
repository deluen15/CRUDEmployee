package com.example.employer.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonUtils {
    private JacksonUtils() {
    }

    public static ObjectMapper cachedEnhancedObjectMapper() {
        return JacksonUtils.BillPughSingleton.INSTANCE;
    }

    public static ObjectMapper newEnhancedObjectMapper() {
        return initObjectMapper();
    }

    private static ObjectMapper initObjectMapper() {
        return (((JsonMapper.builder().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)).configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true)).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).addModule(new JavaTimeModule()).build();
    }

    private static class BillPughSingleton {
        private static final ObjectMapper INSTANCE = JacksonUtils.initObjectMapper();

        private BillPughSingleton() {
        }
    }
}
