package com.example.employer.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
public class Address {

    private @Nullable String city;
    private @Nullable String homeAddress;
    private @Nullable Integer roadNumber;
    private @Nullable Integer postalCode;
}
