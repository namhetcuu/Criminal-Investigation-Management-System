package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suspects")
public class SuspectController {

    @GetMapping
    public ResponseEntity<String> getAllSuspects() {
        return ResponseEntity.ok("List of suspects");
    }

    @PostMapping
    public ResponseEntity<String> addSuspect(@RequestBody String suspect) {
        return ResponseEntity.ok("Suspect added");
    }
}
