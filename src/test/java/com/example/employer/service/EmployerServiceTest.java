package com.example.employer.service;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.exeptions.NotFoundException;
import com.example.employer.model.Address;
import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import com.example.employer.service.imp.EmployerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
    void shouldFindAllEmployersWhenEmployersIsEmpty() {
        // Arrange
        when(this.employerRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<EmployerDTO> actualFindAllEmployersResult = this.employerService.findAllEmployers();

        // Assert
        assertTrue(actualFindAllEmployersResult.isEmpty());
        verify(this.employerRepository).findAll();
    }

    @Test
    void shouldFindAllEmployers() {
        // Arrange
        String email = "Endri.zeqo@gmail.com";
        Address endriAdd = new Address("Leverkusen", "Humboldtstrasse", 21, 51379);
        List<Employer> employers = List.of(
                new Employer(
                        "firstID",
                        "Endri",
                        "Zeqo",
                        email,
                        endriAdd,
                        BigDecimal.valueOf(12L),
                        "working company"
                ));
        given(this.employerRepository.findAll()).willReturn(employers);

        // Act
        List<EmployerDTO> actualFindAllEmployersResult = this.employerService.findAllEmployers();

        // Assert
        assertThat(actualFindAllEmployersResult).isNotEmpty();
        verify(this.employerRepository).findAll();
    }


    @Test
    void testGetEmployerByID() {
        // Arrange
        Employer employer = new Employer();
        employer.setAddress(new Address());
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLName("L Name");
        employer.setName("Name");
        employer.setPhone(BigDecimal.valueOf(42L));
        employer.setWorking_company("Working company");
        Optional<Employer> ofResult = Optional.of(employer);
        when(this.employerRepository.findById((String) any())).thenReturn(ofResult);

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(new Address());
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLName("L Name");
        employerDTO.setName("Name");
        employerDTO.setPhone(BigDecimal.valueOf(42L));
        employerDTO.setWorking_company("Working company");
        when(this.employerMapper.mapEmployerToEmployerDTO((Employer) any())).thenReturn(employerDTO);
        String id = "42";

        // Act
        EmployerDTO actualEmployerByID = this.employerService.getEmployerByID(id);

        // Assert
        assertSame(employerDTO, actualEmployerByID);
        assertEquals("42", actualEmployerByID.getPhone().toString());
        verify(this.employerRepository).findById((String) any());
        verify(this.employerMapper).mapEmployerToEmployerDTO((Employer) any());
    }


    @Test
    void testGetEmployerByID3() {
        // Arrange
        when(this.employerRepository.findById((String) any())).thenReturn(Optional.empty());

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(new Address());
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLName("L Name");
        employerDTO.setName("Name");
        employerDTO.setPhone(BigDecimal.valueOf(42L));
        employerDTO.setWorking_company("Working company");
        when(this.employerMapper.mapEmployerToEmployerDTO((Employer) any())).thenReturn(employerDTO);
        String id = "42";

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.employerService.getEmployerByID(id));
        verify(this.employerRepository).findById((String) any());
    }

    @Test
    void testSaveNewEmployer() {
        // Arrange
        Employer employer = new Employer();
        employer.setAddress(new Address());
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLName("L Name");
        employer.setName("Name");
        employer.setPhone(BigDecimal.valueOf(42L));
        employer.setWorking_company("Working company");
        when(this.employerRepository.insert((Employer) any())).thenReturn(employer);

        Employer employer1 = new Employer();
        employer1.setAddress(new Address());
        employer1.setEmail("jane.doe@example.org");
        employer1.setId("42");
        employer1.setLName("L Name");
        employer1.setName("Name");
        employer1.setPhone(BigDecimal.valueOf(42L));
        employer1.setWorking_company("Working company");
        when(this.employerMapper.mapEmployerDTOtoEmployer((EmployerDTO) any())).thenReturn(employer1);

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(new Address());
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLName("L Name");
        employerDTO.setName("Name");
        employerDTO.setPhone(BigDecimal.valueOf(42L));
        employerDTO.setWorking_company("Working company");

        // Act
        this.employerService.saveNewEmployer(employerDTO);

        // Assert
        verify(this.employerRepository).insert((Employer) any());
        verify(this.employerMapper).mapEmployerDTOtoEmployer((EmployerDTO) any());
    }

    @Test
    void testSaveNewEmployer2() {
        // Arrange
        Employer employer = new Employer();
        employer.setAddress(new Address());
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLName("L Name");
        employer.setName("Name");
        employer.setPhone(BigDecimal.valueOf(42L));
        employer.setWorking_company("Working company");
        when(this.employerRepository.insert((Employer) any())).thenReturn(employer);
        when(this.employerMapper.mapEmployerDTOtoEmployer((EmployerDTO) any())).thenThrow(new NotFoundException("42"));

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(new Address());
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLName("L Name");
        employerDTO.setName("Name");
        employerDTO.setPhone(BigDecimal.valueOf(42L));
        employerDTO.setWorking_company("Working company");

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.employerService.saveNewEmployer(employerDTO));
        verify(this.employerMapper).mapEmployerDTOtoEmployer((EmployerDTO) any());
    }

    @Test
    void testSaveNewEmployer3() {
        // Arrange
        Employer employer = new Employer();
        employer.setAddress(new Address());
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLName("L Name");
        employer.setName("Name");
        employer.setPhone(BigDecimal.valueOf(42L));
        employer.setWorking_company("Working company");
        when(this.employerRepository.insert((Employer) any())).thenReturn(employer);

        Employer employer1 = new Employer();
        employer1.setAddress(new Address());
        employer1.setEmail("jane.doe@example.org");
        employer1.setId("42");
        employer1.setLName("L Name");
        employer1.setName("Name");
        employer1.setPhone(BigDecimal.valueOf(42L));
        employer1.setWorking_company("Working company");
        when(this.employerMapper.mapEmployerDTOtoEmployer((EmployerDTO) any())).thenReturn(employer1);
        EmployerDTO employerDTO = mock(EmployerDTO.class);
        when(employerDTO.getId()).thenReturn("");
        doNothing().when(employerDTO).setAddress((Address) any());
        doNothing().when(employerDTO).setEmail((String) any());
        doNothing().when(employerDTO).setId((String) any());
        doNothing().when(employerDTO).setLName((String) any());
        doNothing().when(employerDTO).setName((String) any());
        doNothing().when(employerDTO).setPhone((BigDecimal) any());
        doNothing().when(employerDTO).setWorking_company((String) any());
        employerDTO.setAddress(new Address());
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLName("L Name");
        employerDTO.setName("Name");
        employerDTO.setPhone(BigDecimal.valueOf(42L));
        employerDTO.setWorking_company("Working company");

        // Act
        this.employerService.saveNewEmployer(employerDTO);

        // Assert
        verify(this.employerRepository).insert((Employer) any());
        verify(this.employerMapper).mapEmployerDTOtoEmployer((EmployerDTO) any());
        verify(employerDTO).getId();
        verify(employerDTO).setAddress((Address) any());
        verify(employerDTO).setEmail((String) any());
        verify(employerDTO, atLeast(1)).setId((String) any());
        verify(employerDTO).setLName((String) any());
        verify(employerDTO).setName((String) any());
        verify(employerDTO).setPhone((BigDecimal) any());
        verify(employerDTO).setWorking_company((String) any());
    }

    @Test
    void testDeleteEmployerByID() {
        // Arrange
        doNothing().when(this.employerRepository).deleteById((String) any());
        String id = "42";

        // Act
        this.employerService.deleteEmployerByID(id);

        // Assert that nothing has changed
        verify(this.employerRepository).deleteById((String) any());
    }

    @Test
    void testDeleteEmployerByID2() {
        // Arrange
        doThrow(new NotFoundException("42")).when(this.employerRepository).deleteById((String) any());
        String id = "42";

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.employerService.deleteEmployerByID(id));
        verify(this.employerRepository).deleteById((String) any());
    }

    @Test
    void testUpdateEmployer() {
        // Arrange
        Employer employer = new Employer();
        employer.setAddress(new Address());
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLName("L Name");
        employer.setName("Name");
        employer.setPhone(BigDecimal.valueOf(42L));
        employer.setWorking_company("Working company");
        when(this.employerRepository.save((Employer) any())).thenReturn(employer);
        when(this.employerRepository.existsById((String) any())).thenReturn(true);

        Employer employer1 = new Employer();
        employer1.setAddress(new Address());
        employer1.setEmail("jane.doe@example.org");
        employer1.setId("42");
        employer1.setLName("L Name");
        employer1.setName("Name");
        employer1.setPhone(BigDecimal.valueOf(42L));
        employer1.setWorking_company("Working company");

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(new Address());
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLName("L Name");
        employerDTO.setName("Name");
        employerDTO.setPhone(BigDecimal.valueOf(42L));
        employerDTO.setWorking_company("Working company");
        when(this.employerMapper.mapEmployerToEmployerDTO((Employer) any())).thenReturn(employerDTO);
        when(this.employerMapper.mapEmployerDTOtoEmployer((EmployerDTO) any())).thenReturn(employer1);
        String id = "42";

        EmployerDTO employerDTO1 = new EmployerDTO();
        employerDTO1.setAddress(new Address());
        employerDTO1.setEmail("jane.doe@example.org");
        employerDTO1.setId("42");
        employerDTO1.setLName("L Name");
        employerDTO1.setName("Name");
        employerDTO1.setPhone(BigDecimal.valueOf(42L));
        employerDTO1.setWorking_company("Working company");

        // Act
        this.employerService.updateEmployer(id, employerDTO1);

        // Assert
        verify(this.employerRepository).existsById((String) any());
        verify(this.employerRepository).save((Employer) any());
        verify(this.employerMapper).mapEmployerToEmployerDTO((Employer) any());
        verify(this.employerMapper).mapEmployerDTOtoEmployer((EmployerDTO) any());
    }

    @Test
    void testUpdateEmployer2() {
        // Arrange
        Employer employer = new Employer();
        employer.setAddress(new Address());
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLName("L Name");
        employer.setName("Name");
        employer.setPhone(BigDecimal.valueOf(42L));
        employer.setWorking_company("Working company");
        when(this.employerRepository.save((Employer) any())).thenReturn(employer);
        when(this.employerRepository.existsById((String) any())).thenReturn(true);

        Employer employer1 = new Employer();
        employer1.setAddress(new Address());
        employer1.setEmail("jane.doe@example.org");
        employer1.setId("42");
        employer1.setLName("L Name");
        employer1.setName("Name");
        employer1.setPhone(BigDecimal.valueOf(42L));
        employer1.setWorking_company("Working company");
        when(this.employerMapper.mapEmployerToEmployerDTO((Employer) any())).thenThrow(new NotFoundException("42"));
        when(this.employerMapper.mapEmployerDTOtoEmployer((EmployerDTO) any())).thenReturn(employer1);
        String id = "42";

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(new Address());
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLName("L Name");
        employerDTO.setName("Name");
        employerDTO.setPhone(BigDecimal.valueOf(42L));
        employerDTO.setWorking_company("Working company");

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.employerService.updateEmployer(id, employerDTO));
        verify(this.employerRepository).existsById((String) any());
        verify(this.employerRepository).save((Employer) any());
        verify(this.employerMapper).mapEmployerToEmployerDTO((Employer) any());
        verify(this.employerMapper).mapEmployerDTOtoEmployer((EmployerDTO) any());
    }
}

