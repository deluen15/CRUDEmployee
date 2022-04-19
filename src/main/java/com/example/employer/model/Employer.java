package com.example.employer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Employer {

    @Id
    private String id;
    private String name;
    private String lName;
    @Indexed(unique = true)
    private String email;
    private Address address;
    private BigDecimal phone;
    private String working_company;
}
