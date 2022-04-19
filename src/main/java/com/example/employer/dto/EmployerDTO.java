package com.example.employer.dto;

import com.example.employer.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class EmployerDTO {
    private String id;
    private String name;
    private String lName;
    private String email;
    private Address address;
    private BigDecimal phone;
    private String working_company;
}
