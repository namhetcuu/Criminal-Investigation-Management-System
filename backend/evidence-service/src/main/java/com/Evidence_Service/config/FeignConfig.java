package com.Evidence_Service.config;

import com.Evidence_Service.util.FeignHeaderProvider;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    private final FeignHeaderProvider headerProvider;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = headerProvider.getAuthToken();
            String apiKey = headerProvider.getApiKey();
            String username = headerProvider.getUsername();
            String permission = headerProvider.getPermissions();

            if (token != null) {
                requestTemplate.header("Authorization", token);
            }

            if (apiKey != null) {
                requestTemplate.header("X-API-KEY", apiKey);
            }

            if (username != null) {
                requestTemplate.header("X-USERNAME", username);
            }

            if (permission != null) {
                requestTemplate.header("X-PERMISSION", permission);
            }
        };
    }
}
