/*
 * @ (#) CorsConfig.java  1.0 7/10/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.apigateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/10/2025
 * @version:    1.0
 */

/**
 * CORS Configuration for API Gateway
 * Standalone configuration without Spring Security
 */

@Configuration
public class CorsConfig {
    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
