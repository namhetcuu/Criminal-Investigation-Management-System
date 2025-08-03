package com.Evidence_Service.controller;

import com.Evidence_Service.dto.EvidenceDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.EvidenceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "X-API-KEY")
@RestController
@RequestMapping("/api/v1/evidences")
@RequiredArgsConstructor
@Tag(name = "Evidence", description = "Management of evidence and related investigation results")
public class EvidenceController {

    private final EvidenceService evidenceService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_EVIDENCE')")
    public ResponseEntity<ApiResponse<EvidenceDTO>> create(@Valid @RequestBody EvidenceDTO dto) {
        ApiResponse<EvidenceDTO> response = ApiResponse.<EvidenceDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Created evidence")
                .data(evidenceService.createEvidence(dto))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{evidenceId}")
    @PreAuthorize("hasAuthority('VIEW_EVIDENCE')")
    public ResponseEntity<ApiResponse<EvidenceDTO>> getByEvidenceId(@Valid @PathVariable String evidenceId) {
        ApiResponse<EvidenceDTO> response = ApiResponse.<EvidenceDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Evidence found")
                .data(evidenceService.getByEvidenceId(evidenceId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-ids")
    @PreAuthorize("hasAuthority('VIEW_EVIDENCE')")
    public ResponseEntity<ApiResponse<List<EvidenceDTO>>> getByEvidenceIds(@Valid @RequestParam List<String> ids) {
        ApiResponse<List<EvidenceDTO>> response = ApiResponse.<List<EvidenceDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Evidence found")
                .data(evidenceService.getByEvidenceIds(ids))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_EVIDENCE')")
    public ResponseEntity<ApiResponse<Page<EvidenceDTO>>> getByCaseOrSuspect(
            @RequestParam(required = false) String caseId,
            @RequestParam(required = false) String suspectId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EvidenceDTO> result = evidenceService.getAllEvidence(pageable);
        ApiResponse<Page<EvidenceDTO>> response = ApiResponse.<Page<EvidenceDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("List evidence by case or suspect")
                .data(result)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{evidenceId}")
    @PreAuthorize("hasAuthority('EDIT_EVIDENCE')")
    public ResponseEntity<ApiResponse<EvidenceDTO>> updateEvidence(@Valid @PathVariable String evidenceId, @Valid @RequestBody EvidenceDTO dto) {
        dto.setEvidenceId(evidenceId);
        ApiResponse<EvidenceDTO> response = ApiResponse.<EvidenceDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Updated evidence")
                .data(evidenceService.updateEvidence(dto))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{evidenceId}")
    @PreAuthorize("hasAuthority('DELETE_EVIDENCE')")
    public ResponseEntity<ApiResponse<Void>> deleteByEvidenceId(@Valid @PathVariable String evidenceId) {
        evidenceService.deleteByEvidenceId(evidenceId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Deleted evidence")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}