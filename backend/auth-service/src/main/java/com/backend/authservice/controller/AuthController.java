package com.backend.authservice.controller;

import com.backend.authservice.dto.request.AuthRequest;
import com.backend.authservice.dto.request.IntrospectRequest;
import com.backend.authservice.dto.response.AuthResponse;
import com.backend.authservice.service.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Tag(name = "Auth Query", description = "Auth API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class AuthController {

    AuthenticationManager aumanager;
    AuthService authService;

    @Operation(
            summary = "Login",
            description = "Authenticate user and return JWT token"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        log.info("Login request for user: {}", request.getUsername());
//        Authentication auth = aumanager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(), request.getPassword()
//                ));
        String result = authService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(result));
    }

    @Operation(
            summary = "Logout",
            description = "Logout user from the system"
    )
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid IntrospectRequest request)
    {
        log.info("AuthController Logout request ");
        authService.logout(request);
        return ResponseEntity.ok("Logout success");
    }
}