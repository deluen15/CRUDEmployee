package com.example.employer.service;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.exeptions.EmployerNotFoundException;
import com.example.employer.repository.EmployerRepository;
import com.example.employer.service.imp.EmployerMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EmployerService.class})
@ExtendWith(SpringExtension.class)
@Slf4j
class EmployerServiceTest {
    @MockBean
    private EmployerMapper employerMapper;

    @MockBean
    private EmployerRepository employerRepository;

    @Autowired
    private EmployerService employerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employerService = new EmployerService(employerRepository, employerMapper);
    }

    @Test
    void shouldFindAllEmployersWhenEmployersIsEmpty() {
        // Arrange
        when(this.employerRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<EmployerDTO> actualFindAllEmployersResult = this.employerService.findAllEmployers();

        // Assert
        assertTrue(actualFindAllEmployersResult.isEmpty());
        verify(this.employerRepository).findAll();
    }

//    @Test
//    @DisplayName("Test for method: EmployerService.findAllEmployers()")
//    void testGetEmployerByID_ValidID_ReturnsEmployerDTO() {
//        // Arrange
//        String id = "123";
//        Employer employer = Employer.builder().build();
//        employer.setId(id);
//
//        when(employerRepository.findById(id)).thenReturn(Optional.of(employer));
//        when(employerMapper.map(employer)).thenReturn(new EmployerDTO());
//
//        // Act
//        EmployerDTO result = employerService.getEmployerByID(id);
//
//        // Assert
//        assertNotNull(result);
//    }

    @Test
    @DisplayName("Test for method: EmployerService.findAllEmployers()")
    void testGetEmployerByID_InvalidID_ThrowsEmployerNotFoundException() {
        // Arrange
        String id = "456";
        when(employerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployerNotFoundException.class, () -> {
            employerService.getEmployerByID(id);
        });
    }
}

