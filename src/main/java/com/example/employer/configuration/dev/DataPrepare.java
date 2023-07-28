package com.example.employer.configuration.dev;

import com.example.employer.model.Employer;
import com.example.employer.service.repository.EmployerRepository;
import com.example.employer.streams.KafkaProducer;
import com.example.employer.utils.JsonUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataPrepare {

    private final EmployerRepository repository;
    private final KafkaProducer producer;

    @PostConstruct
    public void postConstruct() {
        deleteAllEntries();
        createEmployers();
    }

    private void createEmployers() {
        try {
            Employer[] employers = JsonUtils.loadJson("employers.json", Employer[].class).orElseThrow();
            Arrays.stream(employers)
                    .filter(employer -> !repository.existsById(Objects.requireNonNull(employer.getId())))
                    .forEach(employer -> {
                        repository.insert(employer);
                        producer.sendEmployerMessage(employer);
                    });
        } catch (Exception e) {
            log.error("Error while reading employers.json", e);
        }
    }


    private void deleteAllEntries() {
        log.info("Deleting all data");
        repository.deleteAll();
    }

}
