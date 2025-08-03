package com.Evidence_Service.controller;

import com.Evidence_Service.dto.CaseDTO;
import com.Evidence_Service.dto.SuspectDTO;
import com.Evidence_Service.dto.WarrantDTO;
import com.Evidence_Service.dto.EvidenceDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.EvidenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evidences/{evidenceId}")
@Tag(name = "Evidence Assignment", description = "Seizing evidence with the suspect, the case, warrant.")
@RequiredArgsConstructor
public class EvidenceAssignmentController {

    private final EvidenceService evidenceService;

    @PutMapping("/assign-suspect")
    @PreAuthorize("hasAuthority('ASSIGN_SUSPECT')")
    public ApiResponse<EvidenceDTO> assignSuspect(@Valid  @PathVariable String evidenceId, @RequestBody SuspectDTO dto) {
        return ApiResponse.<EvidenceDTO>builder()
                .code(200)
                .message("Assigned suspect")
                .data(evidenceService.assignSuspect(evidenceId, dto))
                .build();
    }

    @PutMapping("/assign-case")
    @PreAuthorize("hasAuthority('ASSIGN_CASE')")
    public ApiResponse<EvidenceDTO> assignCase(@Valid @PathVariable String evidenceId, @RequestBody CaseDTO dto) {
        return ApiResponse.<EvidenceDTO>builder()
                .code(200)
                .message("Assigned case")
                .data(evidenceService.assignCase(evidenceId, dto))
                .build();
    }

    @PutMapping("/assign-warrant")
    @PreAuthorize("hasAuthority('ASSIGN_WARRANT')")
    public ApiResponse<EvidenceDTO> assignWarrant(@Valid @PathVariable String evidenceId, @RequestBody WarrantDTO dto) {
        return ApiResponse.<EvidenceDTO>builder()
                .code(200)
                .message("Assigned warrant")
                .data(evidenceService.assignWarrant(evidenceId, dto))
                .build();
    }

    @GetMapping("/suspects")
    @PreAuthorize("hasAuthority('VIEW_EVIDENCE')")
    public ApiResponse<List<SuspectDTO>> getSuspectsByEvidence(@Valid @PathVariable String evidenceId) {
        return ApiResponse.<List<SuspectDTO>>builder()
                .code(200)
                .message("Suspects fetched")
                .data(evidenceService.getSuspectsByEvidence(evidenceId))
                .build();
    }

    @GetMapping("/warrants")
    @PreAuthorize("hasAuthority('VIEW_EVIDENCE')")
    public ApiResponse<List<WarrantDTO>> getWarrantsByEvidence(@Valid @PathVariable String evidenceId) {
        return ApiResponse.<List<WarrantDTO>>builder()
                .code(200)
                .message("Warrants fetched")
                .data(evidenceService.getWarrantsByEvidence(evidenceId))
                .build();
    }
    @GetMapping("/cases")
    @PreAuthorize("hasAuthority('VIEW_EVIDENCE')")
    public ApiResponse<List<CaseDTO>> getCasesByEvidence(@Valid @PathVariable String evidenceId) {
        return ApiResponse.<List<CaseDTO>>builder()
                .code(200)
                .message("Cases fetched")
                .data(evidenceService.getCasesByEvidence(evidenceId))
                .build();
    }
}