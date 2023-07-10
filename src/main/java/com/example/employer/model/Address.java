package com.example.employer.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;

@Data
@JsonNaming(SnakeCaseStrategy.class)
@Builder(toBuilder = true)
@Schema
public class Address {

    private @Nullable String city;
    private @Nullable String homeAddress;
    private @Nullable Integer roadNumber;
    private @Nullable Integer postalCode;
}
