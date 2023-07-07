package com.example.employer.dto;

import com.example.employer.model.Address;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.math.BigDecimal;

@Data
public class EmployerDTO {

    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String lastName;
    private @Nullable String email;
    private Address address;
    private @Nullable BigDecimal phone;
    private @Nullable String workingCompany;

}
