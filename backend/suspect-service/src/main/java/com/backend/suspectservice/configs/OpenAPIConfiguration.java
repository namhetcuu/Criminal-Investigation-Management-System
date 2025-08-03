/*
 * @(#) $(NAME).java    1.0     2/13/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package com.backend.suspectservice.configs;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:9001");
        server.setDescription("Suspect-Service REST API Documentation");

        Info information = new Info()
                .title("Suspect-Service REST API Documentation")
                .version("1.0")
                .description("This API exposes endpoints to manage suspects.");

        return new OpenAPI().info(information).servers(List.of(server));
    }

}
