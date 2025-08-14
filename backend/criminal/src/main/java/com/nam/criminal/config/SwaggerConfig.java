package com.example.cims.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI cimsOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("CIMS API")
                .description("Crime Investigation Management System API Documentation")
                .version("v1.0")
                .contact(new Contact()
                    .name("Your Name")
                    .email("your.email@example.com"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                .description("CIMS Project Wiki")
                .url("https://github.com/your-repo"));
    }
}
