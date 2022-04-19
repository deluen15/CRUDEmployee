package com.example.employer.service.imp;


import com.example.employer.dto.EmployerDTO;
import com.example.employer.model.Employer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {

    private final ModelMapper modelMapper;

    public EmployerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmployerDTO mapEmployerToEmployerDTO(Employer employer) {
        EmployerDTO employerDTO = modelMapper.map(employer, EmployerDTO.class);
        return employerDTO;
    }

    public Employer mapEmployerDTOtoEmployer(EmployerDTO employerDTO) {
        Employer employer = modelMapper.map(employerDTO, Employer.class);
        return employer;
    }
}
