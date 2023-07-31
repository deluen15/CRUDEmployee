package com.example.employer.controller;

import com.example.employer.model.dto.EmployerDTO;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployerControllerTest {

    private static WireMockServer wireMockServer;
    private static final int wireMockPort = 8089;
    private RestTemplate restTemplate;

    @BeforeAll
    public static void setupClass() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(wireMockPort));
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockPort);
    }

    @AfterAll
    public static void tearDownClass() {
        wireMockServer.stop();
    }

    @BeforeEach
    public void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    void testFindAllEmployers() {
        stubFor(get(urlPathEqualTo("/api/v1/employer/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("[{\"id\":\"1\",\"name\":\"Employer 1\"},{\"id\":\"2\",\"name\":\"Employer 2\"}]")));

        EmployerDTO[] employers = restTemplate.getForObject("http://localhost:" + wireMockPort + "/api/v1/employer/", EmployerDTO[].class);

        assertEquals(2, employers.length);
        assertEquals("1", employers[0].getId());
        assertEquals("Employer 1", employers[0].getName());
        assertEquals("2", employers[1].getId());
        assertEquals("Employer 2", employers[1].getName());
    }

    // Add other functional test methods...
}
