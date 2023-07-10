package com.example.employer.service.imp;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.model.Employer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmployerMapper {

    EmployerDTO map(Employer employer);

    Employer map(EmployerDTO employerDTO);

    void map(@MappingTarget Employer target, EmployerDTO source);

}
