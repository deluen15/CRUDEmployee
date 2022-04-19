package com.example.employer.service;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.exeptions.NotFoundException;
import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import com.example.employer.service.imp.EmployerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class EmployerService {

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;


    public EmployerService(EmployerRepository employerRepository, EmployerMapper employerMapper) {
        this.employerRepository = employerRepository;
        this.employerMapper = employerMapper;
    }

    public List<EmployerDTO> findAllEmployers() {
        return employerRepository.findAll()
                .stream()
                .map(employerMapper::mapEmployerToEmployerDTO)
                .collect(Collectors.toList());
    }

    public EmployerDTO getEmployerByID(String id) {
        return employerRepository.findById(id)
                .map(employerMapper::mapEmployerToEmployerDTO)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void saveNewEmployer(EmployerDTO employerDTO) {
        if (isBlank(employerDTO.getId())) {
            employerDTO.setId(UUID.randomUUID().toString());
        }
        Employer employer = employerMapper.mapEmployerDTOtoEmployer(employerDTO);
        employerRepository.insert(employer);
    }

    public void deleteEmployerByID(String id) {
        employerRepository.deleteById(id);
    }

    public void updateEmployer(String id, EmployerDTO employerDTO) {
        Optional.ofNullable(employerRepository.existsById(id))
                .orElseThrow(() -> new NotFoundException(id));
        Employer employer = employerMapper.mapEmployerDTOtoEmployer(employerDTO);
        employer.setId(id);
        employerRepository.save(employer);
        EmployerDTO updatedEmployer = employerMapper.mapEmployerToEmployerDTO(employer);
    }
}
