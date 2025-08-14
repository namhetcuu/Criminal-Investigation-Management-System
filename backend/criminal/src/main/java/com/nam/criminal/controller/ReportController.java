package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping
    public ResponseEntity<String> getAllReports() {
        return ResponseEntity.ok("List of reports");
    }

    @PostMapping
    public ResponseEntity<String> createReport(@RequestBody String report) {
        return ResponseEntity.ok("Report created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok("Report details: " + id);
    }
}
