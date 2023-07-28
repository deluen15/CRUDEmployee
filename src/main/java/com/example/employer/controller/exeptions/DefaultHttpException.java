package com.example.employer.controller.exeptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.http.HttpStatus;


class DefaultHttpException extends HttpException {
    public DefaultHttpException(@NonNull HttpStatus httpStatus, @Nullable String message, @Nullable Throwable cause) {
        super(httpStatus, message, cause);
    }
}
