/*
 * @(#) $(NAME).java    1.0     2/13/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package com.backend.authservice.configs;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        final String securitySchemeName = "bearerAuth";

        // Server thông qua API Gateway
        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080/api/v1");
        gatewayServer.setDescription("User Management API via Gateway");

        // Server truy cập trực tiếp (cho nhà phát triển)
        Server directServer = new Server();
        directServer.setUrl("http://localhost:8090");
        directServer.setDescription("Direct User Management API");

        Info information = new Info()
                .title("User Management REST API Documentation")
                .version("1.0")
                .description("This API exposes endpoints to manage user.");

        return new OpenAPI().info(information)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .servers(List.of(gatewayServer, directServer));
    }


}
