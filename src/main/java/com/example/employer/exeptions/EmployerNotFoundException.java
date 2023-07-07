package com.example.employer.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployerNotFoundException extends RuntimeException {
    public EmployerNotFoundException(String id) {
        super("Employer with id: " + id + " not found");
    }

}
