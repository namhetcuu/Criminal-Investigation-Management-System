package com.backend.investigationservice.controller;

import com.backend.investigationservice.dto.request.InterviewCreationRequest;
import com.backend.investigationservice.dto.request.InterviewUpdateRequest;
import com.backend.investigationservice.dto.response.InterviewResponse;
import com.backend.investigationservice.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Interview", description = "Interview API")
public class InterviewController {

    private final InterviewService interviewService;

    /**
     * Create a new interview with questions
     */
    @PostMapping
    @Operation(summary = "Create new Interview", description = "Create a new interview with questions")
    @ApiResponse(responseCode = "201", description = "Interview created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<InterviewResponse> createInterview(@Valid @RequestBody InterviewCreationRequest request) {
        InterviewResponse response = interviewService.createInterview(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all interviews by case ID (including questions)
     */
    @GetMapping("/case/{caseId}")
    @Operation(summary = "Get Interviews by Case ID", description = "Retrieve all interviews associated with a specific case ID, including their questions")
    @ApiResponse(responseCode = "200", description = "Interviews retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No interviews found for the given case ID")
    @ApiResponse(responseCode = "400", description = "Invalid case ID")
    public ResponseEntity<List<InterviewResponse>> getByCaseId(@PathVariable UUID caseId) {
        List<InterviewResponse> responses = interviewService.getInterviewsByCaseId(caseId);
        return ResponseEntity.ok(responses);
    }
    /**
     * Get all interviews by case ID Paging(including questions)
     */
    @GetMapping("/by-case")
    @Operation(summary = "Get Interviews by Case ID with Pagination", description = "Retrieve paginated interviews associated with a specific case ID, including their questions")
    @ApiResponse(responseCode = "200", description = "Interviews retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No interviews found for the given case ID")
    @ApiResponse(responseCode = "400", description = "Invalid case ID or pagination parameters")
    public ResponseEntity<Page<InterviewResponse>> getInterviewsByCaseId(
            @RequestParam UUID caseId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(interviewService.getInterviewsByCaseId(caseId, pageable));
    }

    /**
     * Search and paginate interviews by keyword in location
     */
    @GetMapping
    @Operation(summary = "Search Interviews", description = "Search and paginate interviews by keyword in location")
    @ApiResponse(responseCode = "200", description = "Interviews retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    @ApiResponse(responseCode = "404", description = "No interviews found matching the search criteria")
    public ResponseEntity<Page<InterviewResponse>> searchInterviews(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<InterviewResponse> page = interviewService.findAll(keyword, pageable);
        return ResponseEntity.ok(page);
    }
    /**
     * Get an interview by ID
     */
    @GetMapping("/{interviewId}")
    @Operation(summary = "Get Interview by ID", description = "Retrieve an interview by its ID, including its questions")
    @ApiResponse(responseCode = "200", description = "Interview retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Interview not found")
    @ApiResponse(responseCode = "400", description = "Invalid interview ID")
    public ResponseEntity<InterviewResponse> getInterviewById(@PathVariable UUID interviewId) {
        InterviewResponse response = interviewService.getInterviewById(interviewId);
        return ResponseEntity.ok(response);
    }
}
