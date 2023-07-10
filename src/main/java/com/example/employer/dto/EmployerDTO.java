package com.example.employer.dto;

import com.example.employer.model.Address;
import com.example.employer.model.serializer.PhoneNumberSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema
public class EmployerDTO {

    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String lastName;
    private @Nullable String email;
    private Address address;
    @JsonSerialize(using = PhoneNumberSerializer.class)
    private @Nullable String phone;
    private @Nullable String workingCompany;

}
