package com.example.employer.model;

import com.example.employer.model.serializer.PhoneNumberSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "employer")
@JsonNaming(SnakeCaseStrategy.class)
@Schema
@NoArgsConstructor
public class Employer {

    @Id
    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String lastName;
    private @Nullable String email;
    private Address address;
    @JsonSerialize(using = PhoneNumberSerializer.class)
    private @Nullable String phone;
    private @Nullable String workingCompany;


}
