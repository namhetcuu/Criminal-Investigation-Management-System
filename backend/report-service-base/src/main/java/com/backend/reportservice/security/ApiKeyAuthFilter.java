package com.backend.reportservice.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.util.AntPathMatcher;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${internal.api-key}")
    private String internalApiKey;

    private static final List<String> EXCLUDE_URLS = List.of(
            "/api/swagger-ui.html",
            "/api/swagger-ui/**",
            "/api/v3/api-docs",
            "/api/v3/api-docs/**",
            "/api-docs",
            "/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
    );

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        String requestUri = request.getRequestURI();
        System.out.println("Request ServletPath: " + servletPath);
        System.out.println("Request URI: " + requestUri);

        // Kiểm tra khớp đường dẫn với EXCLUDE_URLS
        boolean isExcluded = EXCLUDE_URLS.stream().anyMatch(pattern -> {
            boolean match = pathMatcher.match(pattern, servletPath);
            System.out.println("Checking pattern: " + pattern + " against ServletPath: " + servletPath + " -> Match: " + match);
            return match;
        });

        if (isExcluded) {
            System.out.println("Path " + servletPath + " is excluded, bypassing authentication.");
            filterChain.doFilter(request, response);
            return;
        }

        // Kiểm tra API nội bộ
        if (servletPath.startsWith("/internal/")) {
            String internalKey = request.getHeader("X-Internal-API-Key");
            if (internalKey == null || !internalKey.equals(internalApiKey)) {
                System.out.println("Invalid Internal API Key for path: " + servletPath);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Internal API Key");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

        // Kiểm tra API bên ngoài
        String apiKey = request.getHeader("X-API-KEY");
        if (apiKey == null || !apiKey.equals(internalApiKey)) {
            System.out.println("Invalid API Key for path: " + servletPath);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid API Key");
            return;
        }

        // Thiết lập quyền
        String permissionsHeader = request.getHeader("X-PERMISSION");
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (permissionsHeader != null && !permissionsHeader.isBlank()) {
            String[] perms = permissionsHeader.split(",");
            for (String perm : perms) {
                authorities.add(new SimpleGrantedAuthority(perm.trim()));
            }
        }

        String username = request.getHeader("X-USERNAME");
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
