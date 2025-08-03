package com.Evidence_Service.controller;

import com.Evidence_Service.dto.ForensicInvestResultDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.ForensicInvestResultService;
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
@RequestMapping("/api/v1/evidences/{evidenceId}/forensic-invest")
@Tag(name = "Forensic Investigation", description = "Forensic investigation results")
@RequiredArgsConstructor
public class ForensicInvestResultController {
    private final ForensicInvestResultService forensicInvestResultService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_FORENSIC_RESULT')")
    @Operation(summary = "Create forensic investigation result")
    public ApiResponse<ForensicInvestResultDTO> createForensicInvestResult(@Valid  @PathVariable String evidenceId, @RequestBody ForensicInvestResultDTO dto) {
        return ApiResponse.<ForensicInvestResultDTO>builder().code(201).message("Created").data(forensicInvestResultService.addForensicInvestResult(evidenceId, dto)).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_FORENSIC_RESULT')")
    @Operation(summary = "Get all forensic investigation results")
    public ApiResponse<Page<ForensicInvestResultDTO>> getAllForensicInvestResults(@Valid @PathVariable String evidenceId,
                                                                                  @RequestParam(defaultValue = "0", required = false) int page,
                                                                                  @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ForensicInvestResultDTO> result = forensicInvestResultService.getAllForensicInvestByEvidenceId(evidenceId, pageable);
        return ApiResponse.<Page<ForensicInvestResultDTO>>builder()
                .code(200)
                .message("Fetched")
                .data(result)
                .build();
    }

    @GetMapping("/{resultId}")
    @PreAuthorize("hasAuthority('VIEW_FORENSIC_RESULT')")
    @Operation(summary = "Get forensic investigation result by ID")
    public ApiResponse<ForensicInvestResultDTO> getForensicInvestResultById(@Valid @PathVariable String resultId) {
        return ApiResponse.<ForensicInvestResultDTO>builder().code(200).message("Fetched").data(forensicInvestResultService.getForensicInvestById(resultId)).build();
    }

    @PutMapping("/{resultId}")
    @PreAuthorize("hasAuthority('EDIT_FORENSIC_RESULT')")
    @Operation(summary = "Update forensic investigation result")
    public ApiResponse<ForensicInvestResultDTO> updateForensicInvestResult(@Valid @PathVariable String evidenceId, @PathVariable String resultId, @RequestBody ForensicInvestResultDTO dto) {
        return ApiResponse.<ForensicInvestResultDTO>builder().code(200).message("Updated").data(forensicInvestResultService.updateForensicInvest(evidenceId, resultId, dto)).build();
    }

    @DeleteMapping("/{resultId}")
    @PreAuthorize("hasAuthority('DELETE_FORENSIC_RESULT')")
    @Operation(summary = "Delete forensic investigation result")
    public ApiResponse<Void> deleteForensicInvestResult(@Valid @PathVariable String resultId) {
        forensicInvestResultService.deleteForensicInvestByResultId(resultId);
        return ApiResponse.<Void>builder().code(200).message("Deleted").build();
    }
}

