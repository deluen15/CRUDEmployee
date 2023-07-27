package com.example.employer.service;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.exeptions.HttpExceptionBuilder;
import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import com.example.employer.service.imp.EmployerMapper;
import com.example.employer.streams.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployerService {

    public static final String EMPLOYER_NOT_FOUND = "Employer not found";
    static final Logger log = LoggerFactory.getLogger(EmployerService.class);

    private final EmployerRepository employerRepository;

    private final EmployerMapper mapper;

    private final KafkaProducer producer;

    public List<EmployerDTO> findAllEmployers() {
        List<Employer> employers = employerRepository.findAll();
        return employers.stream()
                .map(mapper::map)
                .toList();
    }

    public EmployerDTO getEmployerByID(String id) {
        return employerRepository.findById(id)
                .map(mapper::map)
                .orElseThrow(() ->
                {
                    log.warn("Employer with ID {} not found", id);
                    return HttpExceptionBuilder.notFound().exceptionMessage(EMPLOYER_NOT_FOUND).build();
                });
    }

    public void saveEmployer(EmployerDTO employerDTO) {
        String id = Optional.ofNullable(employerDTO.getId())
                .filter(s -> !s.isEmpty())
                .orElse(UUID.randomUUID().toString());

        Employer employer = mapper.map(employerDTO);
        employer.setId(id);
        producer.sendEmployerMessage(employer);
        employerRepository.save(employer);
        log.info("Saved new employer with ID: {}", employer.getId());
    }


    public void deleteEmployerByID(String id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> HttpExceptionBuilder.notFound().exceptionMessage(EMPLOYER_NOT_FOUND).build());

        employerRepository.delete(employer);
        log.debug("Deleted employer with ID: {}", id);
    }

    public void updateEmployer(String id, EmployerDTO updatedEmployer) {

        Employer existingEmployer = employerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employer with ID {} not found", id);
                    return HttpExceptionBuilder.notFound().exceptionMessage(EMPLOYER_NOT_FOUND).build();
                });
        mapper.map(existingEmployer, updatedEmployer);
        existingEmployer.setId(updatedEmployer.getId());
        producer.sendEmployerMessage(existingEmployer);
        employerRepository.save(existingEmployer);
        log.debug("Updated employer with ID: {}", id);
    }
}
