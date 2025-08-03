/*
 * @(#) JwtGlobalFilter.java    1.0     4/12/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package com.backend.apigateway.filters;

/*
 * @description: Filter xác thực JWT cho API Gateway
 * @author: Khuong Pham
 * @version: 1.1
 * @created: 7/9/2025
 */

import com.backend.apigateway.model.ErrorMessage;
import com.backend.apigateway.model.OpenApiEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class JwtGlobalFilter implements GlobalFilter, Ordered {

    ReactiveJwtDecoder jwtDecoder;

    @Override
    public int getOrder() {
        return -1; // độ ưu tiên cao
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtGlobalFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Danh sách các đường dẫn không cần xác thực
    private final List<OpenApiEndpoint> openApiEndpoints = Arrays.asList(
            new OpenApiEndpoint("POST", "/api/v1/auth/login"),
            new OpenApiEndpoint("POST", "/api/v1/auth/logout")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Authentication filter is called");
        // Lấy đường dẫn của request
        String path = exchange.getRequest().getURI().getPath();

        // Kiểm tra nếu đường dẫn không yêu cầu xác thực
        String method = exchange.getRequest().getMethod().name();
        logger.info("Request method: {}, path: {}", method, path);
        if (isOpenEndpoint(method, path)) {
            logger.info("No authentication required for path {}", path);
            return chain.filter(exchange);
        }
        // Trích xuất token từ header
        List<String> authHearde = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        // Nếu không có header Authorization, trả về lỗi
        if (CollectionUtils.isEmpty(authHearde)) {
            logger.info("Authentication failed: Missing Authorization header for path {}", path);
            return apiErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    ErrorMessage.MISSING_TOKEN.getCode(),
                    ErrorMessage.MISSING_TOKEN.getMessage()
            );
        }
        String token = authHearde.getFirst().replaceFirst("Bearer ", "");

        return jwtDecoder.decode(token)
                .flatMap(jwt -> {
                    // Token is valid (signature verified and not expired)
                    logger.info("JWT verified successfully: {}", jwt.getSubject());
                    // Forward validated claims to downstream services
                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(builder -> {
                                builder.header("X-Auth-User", jwt.getSubject());
                                // Forward role and permissions if needed
                                if (jwt.getClaim("role") != null) {
                                    builder.header("X-Auth-Role", jwt.getClaim("role").toString());
                                }
                                if (jwt.getClaim("permission") != null) {
                                    builder.header("X-Auth-Permission", jwt.getClaim("permission").toString());
                                }
                            }).build();
                    return chain.filter(mutatedExchange);
                })
                .onErrorResume(e -> {
                    logger.error("JWT validation failed", e);
                    if (e instanceof JwtValidationException) {
                        return apiErrorResponse(exchange, HttpStatus.UNAUTHORIZED, 401,
                                "Invalid token: " + e.getMessage());
                    }
                    return apiErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, 500,
                            "Error processing token: " + e.getMessage());
                });
    }

    // Kiểm tra xem đường dẫn có nằm trong danh sách các endpoint không yêu cầu xác thực hay không
    private boolean isOpenEndpoint(String method, String path) {
        return openApiEndpoints.stream()
                .anyMatch(endpoint -> {
                    if (!endpoint.method().equalsIgnoreCase(method)) {
                        return false;
                    }

                    String pattern = endpoint.path();
                    // Handle ** wildcard
                    if (pattern.endsWith("/**")) {
                        String prefix = pattern.substring(0, pattern.length() - 3);
                        return path.startsWith(prefix);
                    }

                    // Handle other cases like single * or exact matches
                    return pattern.equalsIgnoreCase(path);
                });
    }

    private Mono<Void> apiErrorResponse(ServerWebExchange exchange, HttpStatus status, int code, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date().toString());
        responseBody.put("code", code);
        responseBody.put("message", message);

        try {
            byte[] bytes = objectMapper.writeValueAsString(responseBody).getBytes(StandardCharsets.UTF_8);
            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))
            );
        } catch (Exception e) {
            logger.error("Error writing response", e);
            byte[] bytes = ("{\"code\":" + code + ",\"message\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))
            );
        }
    }

}