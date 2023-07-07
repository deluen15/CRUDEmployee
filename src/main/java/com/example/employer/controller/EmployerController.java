package com.example.employer.controller;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.service.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employer/")
public class EmployerController {
    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping
    public List<EmployerDTO> findAllEmployers() {
        return employerService.findAllEmployers();
    }

    @GetMapping("{id}")
    public EmployerDTO findById(@PathVariable String id) {
        return employerService.getEmployerByID(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable String id) {
        employerService.deleteEmployerByID(id);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStudent(@PathVariable String id, @RequestBody EmployerDTO employerDTO) {
        employerService.upsert(id, employerDTO);
    }
}
