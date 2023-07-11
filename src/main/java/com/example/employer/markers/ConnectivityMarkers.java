package com.example.employer.markers;

import com.example.employer.JsonUtils.JsonUtils;
import net.logstash.logback.marker.Markers;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.MDC;
import org.slf4j.Marker;

public class ConnectivityMarkers {
    private static final String TOPIC = "topic";
    private static final String STATISTICS = "statistics";
    public static final String CORRELATION_TOPIC = "correlation_topic";
    private static final int MAX_MESSAGE_LENGTH = 65536;

    private ConnectivityMarkers() {
    }

    public static Marker combine(final Marker marker, final Marker... markers) {
        for (Marker m : markers) {
            if (m != null) {
                marker.add(m);
            }
        }
        return marker;
    }

    public static @NonNull Marker topic(final @NonNull String topic) {
        return net.logstash.logback.marker.Markers.append(TOPIC, topic);
    }

    public static @NonNull Marker statistics(final @NonNull String identifier) {
        return net.logstash.logback.marker.Markers.append(STATISTICS, identifier);
    }

    public static @NonNull Marker json(final @NonNull String name, final @Nullable Object object) {
        return Markers.append(name, truncate(JsonUtils.toPrettyJson(object).orElse("")));
    }

    private static @NonNull String truncate(final @NonNull String text) {
        return text.length() > MAX_MESSAGE_LENGTH
                ? String.format("%s [... string exceeded max length of %,d; %,d characters were truncated]",
                text.substring(0, MAX_MESSAGE_LENGTH), MAX_MESSAGE_LENGTH, text.length() - MAX_MESSAGE_LENGTH)
                : text;
    }

    public static void setCorrelationTopic(final @NonNull String topic) {
        MDC.put(CORRELATION_TOPIC, topic);
    }

    public static void removeCorrelationTopic() {
        MDC.remove(CORRELATION_TOPIC);
    }
}
