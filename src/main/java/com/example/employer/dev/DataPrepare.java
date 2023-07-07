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
        Address endriAddress = Address.builder()
                .homeAddress("Rruga e Dibres")
                .city("Tirane")
                .postalCode(1001)
                .roadNumber(19)
                .build();

        String email = "Endri.zeqo@gmail.com";
        Employer employer = Employer.builder()
                .id(UUID.randomUUID().toString())
                .name("Endri")
                .lastName("Zeqo")
                .email(email)
                .address(endriAddress)
                .phone(BigDecimal.valueOf(1777500479))
                .workingCompany("19byte")
                .build();
        checkIfEmployerExists(email, employer);

    }

    private void checkIfEmployerExists(String email, Employer employer) {
        repository.findEmployerByEmail(email).ifPresentOrElse(s -> log.debug("Employer already exists"), () -> {
            log.debug("Employer does not exist, creating new one");
            repository.insert(employer);
        });
    }

    private void deleteAllEntries() {
        log.info("Deleting all data");
        repository.deleteAll();
    }

}
