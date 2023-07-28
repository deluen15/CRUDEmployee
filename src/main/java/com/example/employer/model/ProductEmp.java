package com.example.employer.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = "product")
public class ProductEmp {

    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String description;
    private @Nullable String category;
    private @Nullable String price;
    private @Nullable String image;
    private @Nullable String status;
    private @Nullable MetadataEvent metadata;

}
