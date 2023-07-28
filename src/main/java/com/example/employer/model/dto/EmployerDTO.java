package com.example.employer.model.dto;

import com.example.employer.model.Address;
import com.example.employer.model.serializer.PhoneNumberSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO {

    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String lastName;
    private @Nullable String email;
    private Address address;
    @JsonSerialize(using = PhoneNumberSerializer.class)
    private @Nullable String phone;
    private @Nullable String workingCompany;

    public EmployerDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
