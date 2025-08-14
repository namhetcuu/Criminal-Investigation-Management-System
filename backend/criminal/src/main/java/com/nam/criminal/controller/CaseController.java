package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cases")
public class CaseController {

    @GetMapping
    public ResponseEntity<String> getAllCases() {
        return ResponseEntity.ok("List of cases");
    }

    @PostMapping
    public ResponseEntity<String> createCase(@RequestBody String caseData) {
        return ResponseEntity.ok("Case created");
    }
}
