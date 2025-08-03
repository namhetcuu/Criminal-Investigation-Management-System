package com.Evidence_Service.controller;

import com.Evidence_Service.dto.PhysicalInvestResultDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.PhysicalInvestResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evidences/{evidenceId}/physical-invest")
@Tag(name = "Physical Investigation", description = "Physical investigation results")
@RequiredArgsConstructor
public class PhysicalInvestResultController {
    private final PhysicalInvestResultService physicalInvestResultService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_PHYSICAL_RESULT')")
    @Operation(summary = "Create physical investigation result")
    public ApiResponse<PhysicalInvestResultDTO> createPhysicalInvestResult(
            @Valid @PathVariable String evidenceId, @RequestBody PhysicalInvestResultDTO dto) {
        return ApiResponse.<PhysicalInvestResultDTO>builder()
                .code(201)
                .message("Created")
                .data(physicalInvestResultService.addPhysicalInvestResult(evidenceId, dto))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_PHYSICAL_RESULT')")
    @Operation(summary = "Get all physical investigation results")
    public ApiResponse<Page<PhysicalInvestResultDTO>> getAllPhysicalInvestResults(
            @Valid @PathVariable String evidenceId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PhysicalInvestResultDTO> result = physicalInvestResultService.getAllPhysicalInvestByEvidenceId(evidenceId, pageable);
        return ApiResponse.<Page<PhysicalInvestResultDTO>>builder()
                .code(200)
                .message("Fetched")
                .data(result)
                .build();
    }

    @GetMapping("/{resultId}")
    @PreAuthorize("hasAuthority('VIEW_PHYSICAL_RESULT')")
    @Operation(summary = "Get physical investigation result by ID")
    public ApiResponse<PhysicalInvestResultDTO> getPhysicalInvestResultById(
            @Valid @PathVariable String resultId) {
        return ApiResponse.<PhysicalInvestResultDTO>builder()
                .code(200)
                .message("Fetched")
                .data(physicalInvestResultService.getPhysicalInvestByResultId(resultId))
                .build();
    }

    @PutMapping("/{resultId}")
    @PreAuthorize("hasAuthority('EDIT_PHYSICAL_RESULT')")
    @Operation(summary = "Update physical investigation result")
    public ApiResponse<PhysicalInvestResultDTO> updatePhysicalInvestResult(
            @Valid @PathVariable String evidenceId, @PathVariable String resultId, @RequestBody PhysicalInvestResultDTO dto) {
        return ApiResponse.<PhysicalInvestResultDTO>builder()
                .code(200)
                .message("Updated")
                .data(physicalInvestResultService.updatePhysicalInvest(evidenceId, resultId, dto))
                .build();
    }

    @DeleteMapping("/{resultId}")
    @PreAuthorize("hasAuthority('DELETE_PHYSICAL_RESULT')")
    @Operation(summary = "Delete physical investigation result")
    public ApiResponse<Void> deletePhysicalInvestResult(@Valid @PathVariable String resultId) {
        physicalInvestResultService.deletePhysicalInvestByResultId(resultId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Deleted")
                .build();
    }
}