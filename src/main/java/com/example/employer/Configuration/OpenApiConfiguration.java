package com.example.employer.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Employer API", version = "1.0",
        description = "The employer-api is responsible for all tasks related to maintaining the employers",
        contact = @Contact(url = "https://19bytes.de/", name = "19Bytes gmbh")
),
        servers = {
                @Server(description = "DEV", url = "https://api-test.19bytes.de/v1/"),
        }
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@SecurityScheme(name = "apiKeyAuth", type = SecuritySchemeType.APIKEY, paramName = "Ocp-Apim-Subscription-Key", in = SecuritySchemeIn.HEADER)
public class OpenApiConfiguration {

}
