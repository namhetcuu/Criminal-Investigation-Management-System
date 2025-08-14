package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/witnesses")
public class WitnessController {

    @GetMapping
    public ResponseEntity<String> getAllWitnesses() {
        return ResponseEntity.ok("List of witnesses");
    }

    @PostMapping
    public ResponseEntity<String> addWitness(@RequestBody String witness) {
        return ResponseEntity.ok("Witness added");
    }
}
