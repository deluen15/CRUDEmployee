package com.example.employer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String city;
    private String address;
    private Integer road_number;
    private Integer postal_code;
}
