package com.example.employer.exeptions;

import com.example.employer.markers.ConnectivityMarkers;
import com.example.employer.markers.Markers;
import com.example.employer.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Marker;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpException extends RuntimeException {
    private @NonNull Map<String, Object> relatedObjects = new HashMap<>();
    private @NonNull UUID incidentId = UUID.randomUUID();
    private @Nullable String topic = null;
    private @Nullable String statistics = null;
    private @NonNull HttpStatus status;
    private @Nullable String publicMessage;
    private @Nullable String customLogMessage;

    public @NonNull HttpException topic(final String topic) {
        this.topic = topic;
        return this;
    }

    public @NonNull HttpException statistics(final String statistics) {
        this.statistics = statistics;
        return this;
    }

    public @NonNull HttpException publicMessage(final @Nullable String publicMessage) {
        this.publicMessage = publicMessage;
        return this;
    }

    public @NonNull HttpException logMessage(final @Nullable String customLogMessage) {
        this.customLogMessage = customLogMessage;
        return this;
    }

    public @NonNull HttpException append(final @NonNull String name, final @Nullable Object value) {
        this.relatedObjects.put(name, value);
        return this;
    }

    public @NonNull HttpException relatedObjects(@NonNull Map<String, Object> relatedObjects) {
        this.relatedObjects = relatedObjects;
        return this;
    }

    public @NonNull Marker getLogMarkers() {
        Marker marker = Markers.append("incident_id", this.getIncidentId());
        marker.add(Markers.append("class", this.getClass()));
        marker.add(Markers.append("status_code", this.getStatus().value()));
        marker.add(Markers.append("status", this.getStatus().getReasonPhrase()));
        marker.add(Markers.append("public_message", this.getPublicMessage()));
        marker.add(Markers.append("exception_message", super.getMessage()));
        if (this.topic != null) {
            marker.add(ConnectivityMarkers.topic(this.topic));
        }

        if (this.statistics != null) {
            marker.add(ConnectivityMarkers.statistics(this.statistics));
        }

        for (Map.Entry<String, Object> stringObjectEntry : this.relatedObjects.entrySet()) {
            marker.add(Markers.append((stringObjectEntry).getKey(), getRelatedObjectAsString((stringObjectEntry).getValue())));
        }

        return marker;
    }

    private void copy(final @Nullable Throwable cause) {
        if (cause instanceof HttpException e) {
            this.incidentId = UUID.fromString(e.getIncidentId());
            this.topic = e.getTopic();
            this.status = e.getStatus();
            this.publicMessage = e.getPublicMessage();
            this.customLogMessage = e.getPublicMessage();
            this.relatedObjects.putAll(e.getRelatedObjects());
        }

    }

    protected static @Nullable String getRelatedObjectAsString(final @Nullable Object relatedObject) {
        if (relatedObject instanceof String) {
            return (String) relatedObject;
        } else {
            return JsonUtils.toPrettyJson(relatedObject).orElse(String.valueOf(null));
        }
    }

    public HttpException() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.publicMessage = null;
        this.customLogMessage = null;
    }

    public HttpException(final @Nullable String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.publicMessage = null;
        this.customLogMessage = null;
    }

    public HttpException(final @NonNull HttpStatus status, final @Nullable String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.publicMessage = null;
        this.customLogMessage = null;
        this.status = status;
    }

    public HttpException(final @Nullable String message, final @Nullable Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.publicMessage = null;
        this.customLogMessage = null;
        this.copy(cause);
    }

    public HttpException(final @NonNull HttpStatus status, final @Nullable String message, final @Nullable Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.publicMessage = null;
        this.customLogMessage = null;
        this.status = status;
        this.copy(cause);
    }

    public HttpException(final @Nullable Throwable cause) {
        super(cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.publicMessage = null;
        this.customLogMessage = null;
        this.copy(cause);
    }

    public final @Nullable String getTopic() {
        return this.topic;
    }

    public final @NonNull String getIncidentId() {
        return this.incidentId.toString();
    }

    public @NonNull HttpStatus getStatus() {
        return this.status;
    }

    public @Nullable String getPublicMessage() {
        return this.publicMessage;
    }

    public @Nullable String getCustomLogMessage() {
        return this.customLogMessage;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return StringUtils.defaultString(message, this.getStatus().getReasonPhrase());
    }

    public @NonNull String getLogMessage() {
        String message = super.getMessage();
        return StringUtils.defaultIfBlank(this.customLogMessage, StringUtils.defaultIfBlank(message, StringUtils.defaultIfBlank(this.publicMessage, this.getStatus().getReasonPhrase())));
    }

    public @NonNull Map<String, Object> getRelatedObjects() {
        return Collections.unmodifiableMap(this.relatedObjects);
    }

    public @Nullable ErrorResponseBody toErrorResponseBody() {
        return new ErrorResponseBody(this.status.value(), this.status.getReasonPhrase(), this.publicMessage, this.getIncidentId());
    }
}
