package com.example.employer.service;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.exeptions.EmployerNotFoundException;
import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import com.example.employer.service.imp.EmployerMapper;
import com.example.employer.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {EmployerService.class})
@ExtendWith(SpringExtension.class)
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
    @DisplayName("Should find all employers successfully")
    void should_findAllEmployers() {
        // Arrange
        var expectedEmployer = JsonUtils.loadJson("entities/employersList.json", Employer[].class).orElseThrow();

        when(this.employerRepository.findAll()).thenReturn(List.of(expectedEmployer));

        // Act
        var actualFindAllEmployersResult = this.employerService.findAllEmployers();

        // Assert
        verify(this.employerRepository).findAll();

        assertEquals(10, actualFindAllEmployersResult.size());
    }

    @Test
    @DisplayName("Should find all employers when employers list is empty")
    void should_findAllEmployersWhenEmployersIsEmpty() {
        // Arrange
        when(this.employerRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<EmployerDTO> actualFindAllEmployersResult = this.employerService.findAllEmployers();

        // Assert
        assertTrue(actualFindAllEmployersResult.isEmpty());
        verify(this.employerRepository).findAll();
    }

    @Test
    @DisplayName("Should return employer DTO by valid ID")
    void should_returnEmployerDTOByValidID() {
        // Arrange
        var expectedEmployer = JsonUtils.loadJson("entities/employer-test.json", Employer.class).orElseThrow();

        when(employerRepository.findById(Objects.requireNonNull(expectedEmployer.getId()))).thenReturn(Optional.of(expectedEmployer));
        when(employerMapper.map(expectedEmployer)).thenReturn(new EmployerDTO());

        // Act
        EmployerDTO result = employerService.getEmployerByID(expectedEmployer.getId());

        // Assert
        assertNotNull(result);
        assertThat(result).isInstanceOf(EmployerDTO.class);
    }

    @Test
    @DisplayName("Should get employer by ID")
    void should_getEmployerByID() {
        // Arrange
        var expectedEmployer = JsonUtils.loadJson("entities/employer-test.json", Employer.class).orElseThrow();
        var expectedEmployerDTO = JsonUtils.loadJson("entities/employerDTO-test.json", EmployerDTO.class).orElseThrow();

        when(employerRepository.findById(Objects.requireNonNull(expectedEmployer.getId()))).thenReturn(Optional.of(expectedEmployer));

        when(employerMapper.map(expectedEmployer)).thenReturn(expectedEmployerDTO);
        String id = "42";

        // Act
        EmployerDTO actualEmployerByID = employerService.getEmployerByID(expectedEmployer.getId());

        // Assert
        assertSame(expectedEmployerDTO, actualEmployerByID);
        verify(employerRepository).findById(Mockito.any());
        verify(employerMapper).map(Mockito.<Employer>any());
    }

    @Test
    void should_throwEmployerNotFoundExceptionWhenGetEmployerByIdSimple() {
        // Arrange
        when(employerRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        String id = "42";

        // Act and Assert
        assertThrows(EmployerNotFoundException.class, () -> employerService.getEmployerByID(id));
        verify(employerRepository).findById(Mockito.any());
    }

    @Test
    @DisplayName("Should throw EmployerNotFoundException when getting employer by invalid ID")
    void should_throwEmployerNotFoundExceptionWhenGetEmployerByID_InvalidID() {
        // Arrange
        String id = "456";
        when(employerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployerNotFoundException.class, () -> {
            employerService.getEmployerByID(id);
        });
    }

    @Test
    @DisplayName("Should save employer successfully")
    void should_saveEmployerSuccessfully() {
        // Arrange
        var expectedEmployer = JsonUtils.loadJson("entities/employer-test.json", Employer.class).orElseThrow();
        var expectedEmployerDTO = JsonUtils.loadJson("entities/employerDTO-test.json", EmployerDTO.class).orElseThrow();
        when(employerRepository.save(Mockito.any())).thenReturn(expectedEmployer);
        when(employerMapper.map(Mockito.<EmployerDTO>any())).thenReturn(expectedEmployer);

        // Act
        employerService.saveEmployer(expectedEmployerDTO);

        // Assert
        verify(employerRepository).save(Mockito.any());
        verify(employerMapper).map(Mockito.<EmployerDTO>any());
    }

    @Test
    @DisplayName("Should throw EmployerNotFoundException when saving employer")
    void should_throwEmployerNotFoundExceptionWhenSavingEmployer() {
        // Arrange
        when(employerRepository.save(Mockito.any())).thenThrow(new EmployerNotFoundException("42"));

        var expectedEmployer = JsonUtils.loadJson("entities/employer-test.json", Employer.class).orElseThrow();
        var expectedEmployerDTO = JsonUtils.loadJson("entities/employerDTO-test.json", EmployerDTO.class).orElseThrow();

        when(employerMapper.map(Mockito.<EmployerDTO>any())).thenReturn(expectedEmployer);

        // Act and Assert
        assertThrows(EmployerNotFoundException.class, () -> employerService.saveEmployer(expectedEmployerDTO));
        verify(employerRepository).save(Mockito.any());
        verify(employerMapper).map(Mockito.<EmployerDTO>any());
    }

    @Test
    @DisplayName("Delete Employer by ID - Successful")
    void should_testDeleteEmployerByID() {
        // Arrange
        doNothing().when(employerRepository).deleteById(Mockito.any());
        String id = "42";

        // Act
        employerService.deleteEmployerByID(id);

        // Assert
        verify(employerRepository).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Delete Employer by ID - Employer Not Found")
    void testDeleteEmployerByID_EmployerNotFound() {
        // Arrange
        doThrow(new EmployerNotFoundException("42")).when(employerRepository).deleteById(Mockito.any());
        String id = "42";

        // Act and Assert
        assertThrows(EmployerNotFoundException.class, () -> employerService.deleteEmployerByID(id));
        verify(employerRepository).deleteById(Mockito.any());
    }

    @Test
    @DisplayName("Should update employer")
    void should_testUpdateEmployerSuccessfully() {
        // Arrange
        var expectedEmployer = JsonUtils.loadJson("entities/employer-test.json", Employer.class).orElseThrow();
        when(employerRepository.save(Mockito.any())).thenReturn(expectedEmployer);
        when(employerRepository.findById(Mockito.any())).thenReturn(Optional.of(expectedEmployer));
        doNothing().when(employerMapper).map(Mockito.any(), Mockito.any());
        String id = "42";
        var updatedEmployer = JsonUtils.loadJson("entities/employerDTO-test.json", EmployerDTO.class).orElseThrow();

        // Act
        employerService.updateEmployer(id, updatedEmployer);

        // Assert
        verify(employerRepository).save(Mockito.any());
        verify(employerRepository).findById(Mockito.any());
        verify(employerMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should throw EmployerNotFoundException when updating employer")
    void should_testUpdateEmployerEmployerNotFoundException() {
        // Arrange
        when(employerRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        String id = "42";
        var updatedEmployer = JsonUtils.loadJson("entities/employerDTO-test.json", EmployerDTO.class).orElseThrow();

        // Act and Assert
        assertThrows(EmployerNotFoundException.class, () -> employerService.updateEmployer(id, updatedEmployer));
        verify(employerRepository).findById(Mockito.any());
    }

}

