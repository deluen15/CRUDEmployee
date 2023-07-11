package com.example.employer.controller;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.model.Address;
import com.example.employer.model.Employer;
import com.example.employer.repository.EmployerRepository;
import com.example.employer.service.EmployerService;
import com.example.employer.service.imp.EmployerMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EmployerController.class, EmployerService.class})
@ExtendWith(SpringExtension.class)
class EmployerControllerTest {
    @Autowired
    private EmployerController employerController;

    @MockBean
    private EmployerMapper employerMapper;

    @MockBean
    private EmployerRepository employerRepository;

    /**
     * Method under test: {@link EmployerController#deleteStudent(String)}
     */
    @Test
    void testDeleteStudent() throws Exception {
        // Arrange
        doNothing().when(employerRepository).deleteById(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/employer/{id}", "42");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(employerController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link EmployerController#findById(String)}
     */
    @Test
    void testFindById() throws Exception {
        // Arrange
        Address address = new Address();
        address.setCity("Oxford");
        address.setHomeAddress("42 Main St");
        address.setPostalCode(1);
        address.setRoadNumber(10);

        Employer employer = new Employer();
        employer.setAddress(address);
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLastName("Doe");
        employer.setName("Name");
        employer.setPhone("6625550144");
        employer.setWorkingCompany("Working Company");
        Optional<Employer> ofResult = Optional.of(employer);
        when(employerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Address address2 = new Address();
        address2.setCity("Oxford");
        address2.setHomeAddress("42 Main St");
        address2.setPostalCode(1);
        address2.setRoadNumber(10);

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(address2);
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLastName("Doe");
        employerDTO.setName("Name");
        employerDTO.setPhone("6625550144");
        employerDTO.setWorkingCompany("Working Company");
        when(employerMapper.map(Mockito.<Employer>any())).thenReturn(employerDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employer/{id}", "42");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(employerController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"name\":\"Name\",\"last_name\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":{\"city\":\"Oxford\""
                                        + ",\"home_address\":\"42 Main St\",\"road_number\":10,\"postal_code\":1},\"phone\":\"+49 6625550144\",\"working_company"
                                        + "\":\"Working Company\"}"));
    }

    /**
     * Method under test: {@link EmployerController#findById(String)}
     */
    @Test
    void testFindById2() throws Exception {
        // Arrange
        when(employerRepository.findById(Mockito.<String>any())).thenReturn(Optional.empty());

        Address address = new Address();
        address.setCity("Oxford");
        address.setHomeAddress("42 Main St");
        address.setPostalCode(1);
        address.setRoadNumber(10);

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(address);
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLastName("Doe");
        employerDTO.setName("Name");
        employerDTO.setPhone("6625550144");
        employerDTO.setWorkingCompany("Working Company");
        when(employerMapper.map(Mockito.<Employer>any())).thenReturn(employerDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employer/{id}", "42");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(employerController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployerController#findAllEmployers()}
     */
    @Test
    void testFindAllEmployers() throws Exception {
        // Arrange
        when(employerRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employer/");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(employerController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EmployerController#save(EmployerDTO)}
     */
    @Test
    void testSave() throws Exception {
        // Arrange
        when(employerRepository.findAll()).thenReturn(new ArrayList<>());

        Address address = new Address();
        address.setCity("Oxford");
        address.setHomeAddress("42 Main St");
        address.setPostalCode(1);
        address.setRoadNumber(10);

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(address);
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLastName("Doe");
        employerDTO.setName("Name");
        employerDTO.setPhone("6625550144");
        employerDTO.setWorkingCompany("Working Company");
        String content = (new ObjectMapper()).writeValueAsString(employerDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(employerController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EmployerController#update(String, EmployerDTO)}
     */
    @Test
    void testUpdate() throws Exception {
        // Arrange
        Address address = new Address();
        address.setCity("Oxford");
        address.setHomeAddress("42 Main St");
        address.setPostalCode(1);
        address.setRoadNumber(10);

        Employer employer = new Employer();
        employer.setAddress(address);
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLastName("Doe");
        employer.setName("Name");
        employer.setPhone("6625550144");
        employer.setWorkingCompany("Working Company");
        Optional<Employer> ofResult = Optional.of(employer);

        Address address2 = new Address();
        address2.setCity("Oxford");
        address2.setHomeAddress("42 Main St");
        address2.setPostalCode(1);
        address2.setRoadNumber(10);

        Employer employer2 = new Employer();
        employer2.setAddress(address2);
        employer2.setEmail("jane.doe@example.org");
        employer2.setId("42");
        employer2.setLastName("Doe");
        employer2.setName("Name");
        employer2.setPhone("6625550144");
        employer2.setWorkingCompany("Working Company");
        when(employerRepository.save(Mockito.<Employer>any())).thenReturn(employer2);
        when(employerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        doNothing().when(employerMapper).map(Mockito.<Employer>any(), Mockito.<EmployerDTO>any());

        Address address3 = new Address();
        address3.setCity("Oxford");
        address3.setHomeAddress("42 Main St");
        address3.setPostalCode(1);
        address3.setRoadNumber(10);

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(address3);
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLastName("Doe");
        employerDTO.setName("Name");
        employerDTO.setPhone("6625550144");
        employerDTO.setWorkingCompany("Working Company");
        String content = (new ObjectMapper()).writeValueAsString(employerDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/employer/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(employerController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("Employer updated successfully"));
    }

    /**
     * Method under test: {@link EmployerController#update(String, EmployerDTO)}
     */
    @Test
    void testUpdate2() throws Exception {
        // Arrange
        Address address = new Address();
        address.setCity("Oxford");
        address.setHomeAddress("42 Main St");
        address.setPostalCode(1);
        address.setRoadNumber(10);

        Employer employer = new Employer();
        employer.setAddress(address);
        employer.setEmail("jane.doe@example.org");
        employer.setId("42");
        employer.setLastName("Doe");
        employer.setName("Name");
        employer.setPhone("6625550144");
        employer.setWorkingCompany("Working Company");
        when(employerRepository.save(Mockito.<Employer>any())).thenReturn(employer);
        when(employerRepository.findById(Mockito.<String>any())).thenReturn(Optional.empty());
        doNothing().when(employerMapper).map(Mockito.<Employer>any(), Mockito.<EmployerDTO>any());

        Address address2 = new Address();
        address2.setCity("Oxford");
        address2.setHomeAddress("42 Main St");
        address2.setPostalCode(1);
        address2.setRoadNumber(10);

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setAddress(address2);
        employerDTO.setEmail("jane.doe@example.org");
        employerDTO.setId("42");
        employerDTO.setLastName("Doe");
        employerDTO.setName("Name");
        employerDTO.setPhone("6625550144");
        employerDTO.setWorkingCompany("Working Company");
        String content = (new ObjectMapper()).writeValueAsString(employerDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/employer/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(employerController).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

