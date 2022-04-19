package com.example.employer.service.imp;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.model.Employer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Employer")
public interface EmployerMapperMapstruct {
    EmployerMapperMapstruct INSTANCE = Mappers.getMapper(EmployerMapperMapstruct.class);

    EmployerDTO mapEmployerToEmployerDTO(Employer employer);

    Employer mapEmployerDTOToEmployer(EmployerDTO employerDTO);
}
