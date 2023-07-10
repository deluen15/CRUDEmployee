package com.example.employer.dev;

import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataPrepare {

    private final EmployerRepository repository;

    @PostConstruct
    public void postConstruct() {
        deleteAllEntries();
        createEmployers();
    }

    private void createEmployers() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Employer[] employers = objectMapper.readValue(getClass().getResourceAsStream("/employers.json"), Employer[].class);
            Arrays.stream(employers)
                    .filter(employer -> !repository.existsById(employer.getId()))
                    .forEach(repository::insert);
        } catch (Exception e) {
            log.error("Error while reading employers.json", e);
        }
    }

    private void deleteAllEntries() {
        log.info("Deleting all data");
        repository.deleteAll();
    }

}
