package com.example.employer.service.repository;

import com.example.employer.model.ProductEmp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductEmp, String> {
}
