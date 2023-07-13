package com.example.employer.repository;

import com.example.employer.model.Employer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends MongoRepository<Employer, String> {
}
