package com.example.employer.service;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.exeptions.HttpExceptionBuilder;
import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import com.example.employer.service.imp.EmployerMapper;
import com.example.employer.streams.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class EmployerService {

    private final EmployerRepository employerRepository;

    private final EmployerMapper mapper;

    private final @NonNull KafkaProducer producer;

    public List<EmployerDTO> findAllEmployers() {
        List<Employer> employers = employerRepository.findAll();
        log.info("Found {} employers", employers.size());
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
                    return HttpExceptionBuilder.notFound().exceptionMessage("Employer not found").build();
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

        employerRepository.deleteById(id);
        log.debug("Deleted employer with ID: {}", id);
    }

    public void updateEmployer(String id, EmployerDTO updatedEmployer) {

        Employer existingEmployer = employerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employer with ID {} not found", id);
                    return HttpExceptionBuilder.notFound().exceptionMessage("Employer not found").build();
                });
        mapper.map(existingEmployer, updatedEmployer);
        existingEmployer.setId(updatedEmployer.getId());
        producer.sendEmployerMessage(existingEmployer);
        employerRepository.save(existingEmployer);
        log.debug("Updated employer with ID: {}", id);
    }
}
