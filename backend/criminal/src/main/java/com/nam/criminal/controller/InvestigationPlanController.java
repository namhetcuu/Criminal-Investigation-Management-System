package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investigation-plans")
public class InvestigationPlanController {

    @GetMapping
    public ResponseEntity<String> getAllPlans() {
        return ResponseEntity.ok("List of investigation plans");
    }

    @PostMapping
    public ResponseEntity<String> createPlan(@RequestBody String plan) {
        return ResponseEntity.ok("Investigation plan created");
    }
}
