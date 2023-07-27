package com.example.employer.controller;

import com.example.employer.dto.EmployerDTO;
import com.example.employer.service.EmployerService;
import com.example.employer.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class EmployerControllerTest {

    @Mock
    private EmployerService employerService;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllEmployers() throws Exception {
        List<EmployerDTO> employers = Arrays.asList(new EmployerDTO(), new EmployerDTO());
        when(employerService.findAllEmployers()).thenReturn(employers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employer/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        Mockito.verify(employerService, Mockito.times(1)).findAllEmployers();
        Mockito.verifyNoMoreInteractions(employerService);
        Assert.assertEquals(employers.size(), 2);
    }


    @Test
    void testFindById() throws Exception {
        EmployerDTO employer = new EmployerDTO();
        employer.setId("1");
        when(employerService.getEmployerByID(anyString())).thenReturn(employer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employer/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));


        Mockito.verify(employerService, Mockito.times(1)).getEmployerByID("1");
        Mockito.verifyNoMoreInteractions(employerService);
        Assert.assertEquals(employer.getId(), "1");
    }


    @Test
    void testDeleteEmployer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employer/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());


        Mockito.verify(employerService, Mockito.times(1)).deleteEmployerByID("1");

        Mockito.verify(employerService, Mockito.times(1)).getEmployerByID("1");
        Mockito.verifyNoMoreInteractions(employerService);
    }


    @Test
    void testSaveEmployer() throws Exception {

        EmployerDTO employerDTO = JsonUtils.loadJson("entities/employerDTO-test.json", EmployerDTO.class).orElseThrow();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(employerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(employerService, Mockito.times(1)).saveEmployer(employerDTO);
        Mockito.verify(employerService, Mockito.times(1)).saveEmployer(Mockito.argThat(savedEmployerDTO -> {
            // Add assertions on the saved employerDTO object
            Assert.assertEquals(employerDTO.getId(), savedEmployerDTO.getId());
            Assert.assertEquals(employerDTO.getName(), savedEmployerDTO.getName());
            Assert.assertEquals(employerDTO.getLastName(), savedEmployerDTO.getLastName());

            return true;
        }));

    }

    @Test
    void testUpdateEmployer() throws Exception {
        EmployerDTO employerDTO = JsonUtils.loadJson("src/test/resources/employer.json", EmployerDTO.class).orElseThrow();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employer/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(employerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(employerService, Mockito.times(1)).updateEmployer(Mockito.eq("1"), Mockito.argThat(updatedEmployerDTO -> {

            Assert.assertEquals(employerDTO.getId(), updatedEmployerDTO.getId());
            Assert.assertEquals(employerDTO.getName(), updatedEmployerDTO.getName());
            Assert.assertEquals(employerDTO.getLastName(), updatedEmployerDTO.getLastName());

            return true;
        }));
    }

}
