/*
 * @ (#) JwtDecoderConfig.java  1.0 7/11/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.apigateway.configuration;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.web.reactive.function.client.WebClient;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/11/2025
 * @version:    1.0
 */
@Configuration
public class JwtDecoderConfig {

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        // Tạo WebClient có hỗ trợ load balancer
        WebClient webClient = WebClient.builder()
                .filter(lbFunction) // Filter này cho phép hiểu URL lb://
                .build();

        return NimbusReactiveJwtDecoder.withJwkSetUri("lb://auth-service/.well-known/jwks.json")
                .webClient(webClient)
                .build();
    }
}
