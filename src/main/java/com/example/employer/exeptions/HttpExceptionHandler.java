package com.example.employer.exeptions;

import com.example.employer.utils.JsonUtils;
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

import java.util.Map;

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
        if (status.is5xxServerError()) {
            log.error(marker, e.getLogMessage(), e);
        } else if (status.is4xxClientError()) {
            log.warn(marker, e.getLogMessage(), e);
        } else if (status.is3xxRedirection()) {
            log.info(marker, e.getLogMessage(), e);
        } else {
            log.debug(marker, e.getLogMessage(), e);
        }

        return new ResponseEntity<>(e.toErrorResponseBody(), getResponseHeaders(), e.getStatus());
    }

    private static @NonNull HttpHeaders getResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static @NonNull String toPrettyJson(final @NonNull Map<String, ?> message) {
        String json = JsonUtils.toPrettyJson(message).orElse("");
        return json.length() > 65536 ? String.format("%s [... string exceeded max length of %,d; %,d characters were truncated]", json.substring(0, 65536), 65536, json.length() - 65536) : json;
    }
}
