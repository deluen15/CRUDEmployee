package com.example.employer.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Employer API", version = "1.0",
        description = "The employer-api is responsible for all tasks related to maintaining the employers",
        contact = @Contact(url = "https://19bytes.de/", name = "19Bytes gmbh")
),
        servers = {
                @Server(description = "DEV", url = "http://localhost:8080"),
        }
)
public class OpenApiConfiguration {

}
