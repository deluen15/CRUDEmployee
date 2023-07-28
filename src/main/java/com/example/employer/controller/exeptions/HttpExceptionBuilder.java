package com.example.employer.controller.exeptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpExceptionBuilder {
    private final @NonNull HttpStatus status;
    private @Nullable String topic = null;
    private @Nullable String statistics = null;
    private @Nullable String publicMessage = null;
    private @Nullable String customLogMessage = null;
    private @Nullable String exceptionMessage = null;
    private @Nullable Throwable cause = null;
    private final @NonNull Map<String, Object> loggingObjects = new HashMap<>();

    private HttpExceptionBuilder(@NonNull HttpStatus status) {
        this.status = status;
    }

    public static HttpExceptionBuilder of(@NonNull HttpStatus status) {
        return new HttpExceptionBuilder(status);
    }

    public static HttpExceptionBuilder badRequest() {
        return (new HttpExceptionBuilder(HttpStatus.BAD_REQUEST)).publicMessage("unspecific client error indicating that the server cannot process the request due to something that is perceived to be a client error (e.g. malformed request syntax, invalid request)");
    }

    public static HttpExceptionBuilder unauthorized() {
        return (new HttpExceptionBuilder(HttpStatus.UNAUTHORIZED)).publicMessage("User is not authenticated or token has been expired");
    }

    public static HttpExceptionBuilder forbidden() {
        return (new HttpExceptionBuilder(HttpStatus.FORBIDDEN)).publicMessage("Not allowed to access the requested resource");
    }

    public static HttpExceptionBuilder notFound() {
        return (new HttpExceptionBuilder(HttpStatus.NOT_FOUND)).publicMessage("The requested resource could not be found");
    }

    public static HttpExceptionBuilder conflict() {
        return (new HttpExceptionBuilder(HttpStatus.CONFLICT)).publicMessage("The requested resource already exists");
    }

    public static HttpExceptionBuilder internalServerError() {
        return (new HttpExceptionBuilder(HttpStatus.INTERNAL_SERVER_ERROR)).publicMessage("An unexpected error occured");
    }

    public HttpExceptionBuilder topic(String topic) {
        this.topic = topic;
        return this;
    }

    public HttpExceptionBuilder statistics(String statistics) {
        this.statistics = statistics;
        return this;
    }

    public HttpExceptionBuilder exceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public HttpExceptionBuilder cause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public HttpExceptionBuilder customLogMessage(String customLogMessage) {
        this.customLogMessage = customLogMessage;
        return this;
    }

    public HttpExceptionBuilder publicMessage(String publicMessage) {
        this.publicMessage = publicMessage;
        return this;
    }

    public HttpExceptionBuilder logObject(String key, Object value) {
        this.loggingObjects.put(key, value);
        return this;
    }

    public HttpException build() {
        HttpException httpException = new DefaultHttpException(this.status, this.exceptionMessage, this.cause);
        return httpException.logMessage(this.customLogMessage).topic(this.topic).publicMessage(this.publicMessage).statistics(this.statistics).relatedObjects(this.loggingObjects);
    }
}
