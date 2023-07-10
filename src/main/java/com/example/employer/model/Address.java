package com.example.employer.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

@Data
@JsonNaming(SnakeCaseStrategy.class)
@Schema
@NoArgsConstructor
public class Address {

    private @Nullable String city;
    private @Nullable String homeAddress;
    private @Nullable Integer roadNumber;
    private @Nullable Integer postalCode;
}
