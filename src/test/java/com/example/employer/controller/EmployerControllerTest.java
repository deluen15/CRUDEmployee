package com.example.employer.controller;

import com.example.employer.model.dto.EmployerDTO;
import com.example.employer.service.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployerControllerTest {

    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;

    private MockMvc mockMvc;

    // Helper method to create EmployerDTO objects
    private EmployerDTO createEmployerDTO(String id, String name) {
        return new EmployerDTO(id, name);
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employerController).build();
    }

    @Test
    void testFindAllEmployers() throws Exception {
        List<EmployerDTO> employers = Arrays.asList(
                createEmployerDTO("1", "Employer 1"),
                createEmployerDTO("2", "Employer 2")
        );

        when(employerService.findAllEmployers()).thenReturn(employers);

        mockMvc.perform(get("/api/v1/employer/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(employerService).findAllEmployers();
    }

    @Test
    void testFindById() throws Exception {
        EmployerDTO employer = createEmployerDTO("1", "Employer 1");

        when(employerService.getEmployerByID("1")).thenReturn(employer);

        mockMvc.perform(get("/api/v1/employer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Employer 1"));

        verify(employerService).getEmployerByID("1");
    }

    @Test
    void testSave() throws Exception {
        EmployerDTO employer = createEmployerDTO("1", "New Employer");

        mockMvc.perform(post("/api/v1/employer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"New Employer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employer saved successfully"));

        verify(employerService).saveEmployer(employer);
    }

    @Test
    void testUpdate() throws Exception {
        EmployerDTO employer = createEmployerDTO("1", "Updated Employer");

        mockMvc.perform(put("/api/v1/employer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Updated Employer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employer updated successfully"));

        verify(employerService).updateEmployer("1", employer);
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/v1/employer/1"))
                .andExpect(status().isOk());

        verify(employerService).deleteEmployerByID("1");
    }
}
