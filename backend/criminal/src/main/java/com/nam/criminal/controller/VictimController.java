package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/victims")
public class VictimController {

    @GetMapping
    public ResponseEntity<String> getAllVictims() {
        return ResponseEntity.ok("List of victims");
    }

    @PostMapping
    public ResponseEntity<String> addVictim(@RequestBody String victim) {
        return ResponseEntity.ok("Victim added");
    }
}
