package com.example.employer.controller;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.service.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employer/")
public class EmployerController {
    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("hello")
    public String helloWorld() {
        return "Welcome to my World";
    }

    @GetMapping
    public List<EmployerDTO> findAllEmployers() {
        return employerService.findAllEmployers();
    }

    @GetMapping("{id}")
    public EmployerDTO findStudentByID(@PathVariable String id) {
        return employerService.getEmployerByID(id);
    }

    @PostMapping
    public ResponseEntity<Void> saveNewStudent(@RequestBody EmployerDTO employerDTO) {
        employerService.saveNewEmployer(employerDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        employerService.deleteEmployerByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("{id}")
    public void updateStudent(@PathVariable String id, @RequestBody EmployerDTO employerDTO) {
        employerService.updateEmployer(id, employerDTO);
    }

}
