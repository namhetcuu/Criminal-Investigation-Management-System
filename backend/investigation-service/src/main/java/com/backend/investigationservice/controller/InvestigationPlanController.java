package com.backend.investigationservice.controller;

import com.backend.investigationservice.dto.request.InvestigationPlanCreationRequest;
import com.backend.investigationservice.dto.response.InvestigationPlanResponse;
import com.backend.investigationservice.service.InvestigationPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/investigation-plans")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Investigation Plan", description = "Investigation Plan API")
public class InvestigationPlanController {

    private final InvestigationPlanService investigationPlanService;

    // GET ALL (non-deleted)
    @GetMapping
    @Operation(summary = "Get all Investigation Plans", description = "Retrieve all non-deleted investigation plans")
    @ApiResponse(responseCode = "201", description = "Investigation plans retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "No investigation plans found")
    public ResponseEntity<List<InvestigationPlanResponse>> getAllPlans() {
        List<InvestigationPlanResponse> plans = investigationPlanService.findAll();
        return ResponseEntity.ok(plans);
    }

    // GET paginated with keyword search
    @GetMapping("/search")
    @Operation(summary = "Search Investigation Plans", description = "Search investigation plans by keyword with pagination")
    @ApiResponse(responseCode = "200", description = "Investigation plans retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "No investigation plans found")
    public ResponseEntity<Page<InvestigationPlanResponse>> searchPlans(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        Page<InvestigationPlanResponse> plans = investigationPlanService.findAll(keyword, pageable);
        return ResponseEntity.ok(plans);
    }

    // GET by caseId
    @GetMapping("/case/{caseId}")
    @Operation(summary = "Get Investigation Plans by Case ID", description = "Retrieve all investigation plans associated with a specific case ID")
    @ApiResponse(responseCode = "200", description = "Investigation plans retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No investigation plans found for the given case ID")
    @ApiResponse(responseCode = "400", description = "Invalid case ID")
    public ResponseEntity<List<InvestigationPlanResponse>> getPlansByCaseId(@PathVariable UUID caseId) {
        List<InvestigationPlanResponse> plans = investigationPlanService.getByCaseId(caseId);
        return ResponseEntity.ok(plans);
    }

    // GET paginated by caseId
    @GetMapping("/by-case")
    @Operation(summary = "Get Investigation Plans by Case ID with Pagination", description = "Retrieve paginated investigation plans associated with a specific case ID")
    @ApiResponse(responseCode = "200", description = "Investigation plans retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No investigation plans found for the given case ID")
    @ApiResponse(responseCode = "400", description = "Invalid case ID or pagination parameters")
    public ResponseEntity<Page<InvestigationPlanResponse>> getByCaseId(
            @RequestParam UUID caseId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(investigationPlanService.getByCaseId(caseId, pageable));
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Create Investigation Plan", description = "Create a new investigation plan")
    @ApiResponse(responseCode = "201", description = "Investigation plan created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "Investigation plan creation failed")
    public ResponseEntity<InvestigationPlanResponse> createPlan(
            @RequestBody @Valid InvestigationPlanCreationRequest request
    ) {
        InvestigationPlanResponse created = investigationPlanService.createPlan(request);
        return ResponseEntity.ok(created);
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Update Investigation Plan", description = "Update an existing investigation plan")
    @ApiResponse(responseCode = "200", description = "Investigation plan updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "Investigation plan not found")
    public ResponseEntity<InvestigationPlanResponse> updatePlan(
            @PathVariable UUID id,
            @RequestBody @Valid InvestigationPlanCreationRequest request
    ) {
        InvestigationPlanResponse updated = investigationPlanService.updatePlan(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE (soft delete)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Investigation Plan", description = "Soft delete an investigation plan by ID")
    @ApiResponse(responseCode = "200", description = "Investigation plan deleted successfully")
    @ApiResponse(responseCode = "404", description = "Investigation plan not found")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<InvestigationPlanResponse> deletePlan(@PathVariable UUID id) {
        InvestigationPlanResponse deleted = investigationPlanService.deletePlan(id);
        return ResponseEntity.ok(deleted);
    }
} 