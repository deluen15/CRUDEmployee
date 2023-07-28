package com.example.employer.utils;

import com.example.employer.utils.markers.ConnectivityMarkers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class JsonUtils {
    private static final ObjectMapper defaultObjectMapper = JacksonUtils.cachedEnhancedObjectMapper();
    private static final ObjectMapper prettyObjectMapper = JacksonUtils.newEnhancedObjectMapper();
    public static final String JSON_UTILS = "jsonUtils";

    private JsonUtils() {
    }

    public static Optional<String> toJson(@Nullable Object object) {
        try {
            return Optional.of(defaultObjectMapper.writeValueAsString(object));
        } catch (JsonProcessingException var2) {
            log.warn(ConnectivityMarkers.topic(JSON_UTILS), "Error creating JSON from object.", var2);
            return Optional.empty();
        }
    }

    public static Optional<String> toPrettyJson(@Nullable Object object) {
        try {
            return Optional.of(prettyObjectMapper.writeValueAsString(object));
        } catch (JsonProcessingException var2) {
            log.warn(ConnectivityMarkers.topic(JSON_UTILS), "Error creating JSON from object.", var2);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> fromJson(@Nullable String json, @NonNull Class<T> type) {
        if (StringUtils.isNotBlank(json)) {
            try {
                return Optional.ofNullable(defaultObjectMapper.readValue(json, type));
            } catch (IOException var3) {
                log.warn(ConnectivityMarkers.combine(Markers.append("json", json), Markers.append("type", type.getName()), ConnectivityMarkers.topic(JSON_UTILS)), "Error creating object from JSON: " + json, var3);
            }
        }

        return Optional.empty();
    }

    public static <T> Optional<T> fromJson(@Nullable String json, @NonNull TypeReference<T> type) {
        if (StringUtils.isNotBlank(json)) {
            try {
                return Optional.ofNullable(defaultObjectMapper.readValue(json, type));
            } catch (IOException var3) {
                log.warn(ConnectivityMarkers.combine(Markers.append("json", json), Markers.append("type", type.toString()), ConnectivityMarkers.topic(JSON_UTILS)), "Error creating object from JSON: {}", json, var3);
            }
        }

        return Optional.empty();
    }

    public static Optional<Object> fromJson(@Nullable String json) {
        return fromJson(json, Object.class);
    }

    private static boolean isJsonImpl(@NonNull String text) {
        try {
            defaultObjectMapper.readTree(text);
            return true;
        } catch (IOException var2) {
            return false;
        }
    }

    public static boolean isJson(@NonNull String text, @NonNull JsonType type) {
        String trimmedText = StringUtils.trim(text);
        if (type == JsonUtils.JsonType.OBJECT) {
            return StringUtils.startsWith(trimmedText, "{") && StringUtils.endsWith(trimmedText, "}") && isJsonImpl(text);
        } else if (type != JsonUtils.JsonType.ARRAY) {
            return isJsonImpl(trimmedText);
        } else {
            return StringUtils.startsWith(trimmedText, "[") && StringUtils.endsWith(trimmedText, "]") && isJsonImpl(text);
        }
    }

    public static @Nullable String prettifyJson(@Nullable String json) {
        if (json == null) {
            return null;
        } else {
            Optional<Object> object = fromJson(json, Object.class);
            return object.map(o -> toPrettyJson(o).orElse(json)).orElse(json);
        }
    }

    public static Optional<String> loadJson(@NonNull String path) {
        try {
            InputStream jsonStream = loadJsonAsStream(path);
            return Optional.ofNullable(IOUtils.toString(jsonStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.warn(ConnectivityMarkers.combine(Markers.append("path", path), ConnectivityMarkers.topic(JSON_UTILS)), "Error loading JSON from file.", e);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> loadJson(@NonNull String path, @NonNull Class<T> type) {
        return loadJson(path)
                .flatMap(json -> fromJson(json, type));
    }

    public static @NonNull InputStream loadJsonAsStream(@NonNull String path) {
        return Objects.requireNonNull(JsonUtils.class.getClassLoader().getResourceAsStream(path));
    }

    static {
        prettyObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        prettyObjectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public enum JsonType {
        OBJECT,
        ARRAY,
        OBJECT_OR_ARRAY;

        private JsonType() {
        }
    }
}
