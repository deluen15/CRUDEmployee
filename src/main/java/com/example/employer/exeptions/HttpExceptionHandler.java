package com.example.employer.exeptions;

import lombok.Generated;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class HttpExceptionHandler {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(HttpExceptionHandler.class);

    public HttpExceptionHandler() {
    }

    @ResponseBody
    @ExceptionHandler({HttpException.class})
    public @NonNull ResponseEntity<ErrorResponseBody> handleHttpException(final @NonNull HttpException e) {
        HttpStatus status = e.getStatus();
        Marker marker = e.getLogMarkers();
        String logMessage = e.getLogMessage();
        Throwable cause = e.getCause();

        switch (status.series()) {
            case SERVER_ERROR -> log.error(marker, logMessage, cause);
            case CLIENT_ERROR -> log.warn(marker, logMessage, cause);
            case REDIRECTION -> log.info(marker, logMessage, cause);
            default -> log.debug(marker, logMessage, cause);
        }

        return new ResponseEntity<>(e.toErrorResponseBody(), getResponseHeaders(), status);
    }

    private static @NonNull HttpHeaders getResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
