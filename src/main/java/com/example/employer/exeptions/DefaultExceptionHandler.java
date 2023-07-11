package com.example.employer.exeptions;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DefaultExceptionHandler extends HttpExceptionHandler {
    @Override
    @Primary
    @ExceptionHandler(HttpException.class)
    @ResponseBody
    @ApiResponse(description = "error occurred - see status code and error object for more information.",
            content = @Content(mediaType = "application/x.error+json;version=1",
                    schema = @Schema(implementation = ErrorResponseBody.class)))
    public @NonNull ResponseEntity<ErrorResponseBody> handleHttpException(
            final @NonNull HttpException e) {
        return super.handleHttpException(e);
    }
}
