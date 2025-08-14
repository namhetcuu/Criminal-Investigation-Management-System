package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evidence")
public class EvidenceController {

    @GetMapping
    public ResponseEntity<String> getAllEvidence() {
        return ResponseEntity.ok("List of evidence");
    }

    @PostMapping
    public ResponseEntity<String> addEvidence(@RequestBody String evidence) {
        return ResponseEntity.ok("Evidence added");
    }
}
