package com.example.employer.repository;

import com.example.employer.model.Employer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends MongoRepository<Employer, String> {
    Optional<Employer> findEmployerByEmail(String email);
}
