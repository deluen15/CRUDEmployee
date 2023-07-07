package com.example.employer.service;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.exeptions.EmployerNotFoundException;
import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import com.example.employer.service.imp.EmployerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployerService {

    private final EmployerRepository employerRepository;

    private final EmployerMapper mapper;


    public List<EmployerDTO> findAllEmployers() {
        List<Employer> employers = employerRepository.findAll();
        log.debug("Found {} employers", employers.size());
        return employers.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public EmployerDTO getEmployerByID(String id) {
        return employerRepository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> {
                    log.warn("Employer with ID {} not found", id);
                    return new EmployerNotFoundException(id);
                });
    }

    public void upsert(String id, EmployerDTO employerDTO) {
        if (isBlank(employerDTO.getId())) {
            employerDTO.setId(UUID.randomUUID().toString());
        }

        Optional<Employer> optionalEmployer = employerRepository.findById(id);
        Employer existingEmployer;

        if (optionalEmployer.isPresent()) {
            existingEmployer = optionalEmployer.get();
        } else {
            log.warn("Employer not found for ID: {}", id);
            return;
        }

        mapper.map(employerDTO, existingEmployer);
        employerRepository.save(existingEmployer);

        if (isBlank(id)) {
            log.debug("Saved new employer with ID: {}", existingEmployer.getId());
        } else {
            log.debug("Updated employer with ID: {}", id);
        }
    }


    public void deleteEmployerByID(String id) {
        employerRepository.deleteById(id);
        log.debug("Deleted employer with ID: {}", id);
    }

//    public void updateEmployer(String id, EmployerDTO updatedEmployer) {
//        Employer existingEmployer = employerRepository.findById(id)
//                .orElseThrow(() -> {
//                    log.warn("Employer not found for ID: {}", id);
//                    return new EmployerNotFoundException(id);
//                });
//        mapper.map(updatedEmployer, existingEmployer);
//        employerRepository.save(existingEmployer);
//        log.debug("Updated employer with ID: {}", id);
//    }
}
