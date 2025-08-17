package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String request) {
        return ResponseEntity.ok("Login successful!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String request) {
        return ResponseEntity.ok("User registered!");
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser() {
        return ResponseEntity.ok("Current user info");
    }
}
