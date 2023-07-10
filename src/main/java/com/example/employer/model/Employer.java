package com.example.employer.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@Document(collection = "employer")
@JsonNaming(SnakeCaseStrategy.class)
@Schema
public class Employer {

    @Id
    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String lastName;
    @Indexed(unique = true)
    private @Nullable String email;
    private Address address;
    private @Nullable BigDecimal phone;
    private @Nullable String workingCompany;
}
