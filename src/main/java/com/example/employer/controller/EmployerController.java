package com.example.employer.controller;

import com.example.employer.model.ProductEmp;
import com.example.employer.model.dto.EmployerDTO;
import com.example.employer.service.EmployerService;
import com.example.employer.service.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employer/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8082", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EmployerController {

    private final @NonNull EmployerService employerService;
    private final @NonNull ProductRepository productRepository;

    @GetMapping("product")
    public List<ProductEmp> findAllProduct() {
        return productRepository.findAll();
    }

    @GetMapping
    @Operation(summary = "Find all Employers",
            tags = {"Employer"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmployerDTO.class)))}
    )
    public List<EmployerDTO> findAllEmployers() {
        return employerService.findAllEmployers();
    }

    @GetMapping(value = "{id}", produces = "application/json")
    @Operation(summary = "Find Employer by ID",
            tags = {"Employer"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmployerDTO.class)))}
    )
    public EmployerDTO findById(@PathVariable String id) {
        return employerService.getEmployerByID(id);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete Employer by ID",
            tags = {"Employer"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmployerDTO.class)))}
    )
    public void deleteStudent(@PathVariable String id) {
        employerService.deleteEmployerByID(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(produces = "application/json")
    @Operation(summary = "Save and update Employer by ID",
            tags = {"Employer"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmployerDTO.class)))}
    )
    public String save(@RequestBody EmployerDTO employerDTO) {
        employerService.saveEmployer(employerDTO);
        return "Employer saved successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "{id}", produces = "application/json")
    @Operation(summary = "Update Employer by ID",
            tags = {"Employer"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmployerDTO.class)))}
    )
    public String update(@PathVariable @NonNull String id, @RequestBody EmployerDTO employerDTO) {
        employerService.updateEmployer(id, employerDTO);
        return "Employer updated successfully";
    }
}
