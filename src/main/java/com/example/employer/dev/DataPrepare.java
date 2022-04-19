package com.example.employer.dev;

import com.example.employer.model.Address;
import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataPrepare {
    private final EmployerRepository repository;


    @PostConstruct
    public void postConstruct() {
        deleteAllEntries();
        createEmployer();
    }

    private void createEmployer() {
        Address endriAdd = new Address("Leverkusen", "Humboldtstrasse", 21, 51379);

        String email = "Endri.zeqo@gmail.com";
        Employer employer = new Employer(UUID.randomUUID().toString(),
                "Endri",
                "Zeqo",
                email,
                endriAdd,
                BigDecimal.valueOf(1777500479),
                "19byte");
        checkIfEmployerExists(email, employer);

    }

    private void checkIfEmployerExists(String email, Employer employer) {
        repository.findEmployerByEmail(email).ifPresentOrElse(s -> {
            System.out.println("emplyer exists" + s);
        }, () -> {
            System.out.println("Inserting Student" + employer);
            repository.insert(employer);
        });
    }

    private void deleteAllEntries() {
        log.info("Deleting all data");
        repository.deleteAll();
    }

}
