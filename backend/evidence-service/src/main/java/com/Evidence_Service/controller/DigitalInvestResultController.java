package com.Evidence_Service.controller;
import com.Evidence_Service.dto.DigitalInvestResultDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.DigitalInvestResultService;
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
@RequestMapping("/api/v1/evidences/{evidenceId}/digital-invest")
@Tag(name = "Digital Investigation", description = "Digital investigation results management")
@RequiredArgsConstructor
public class DigitalInvestResultController {

    private final DigitalInvestResultService digitalInvestResultService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_DIGITAL_RESULT')")
    @Operation(summary = "Create digital investigation result")
    public ApiResponse<DigitalInvestResultDTO> createDigitalInvestResult(@Valid @PathVariable String evidenceId, @RequestBody DigitalInvestResultDTO dto) {
        return ApiResponse.<DigitalInvestResultDTO>builder()
                .code(201)
                .message("Digital investigation result created")
                .data(digitalInvestResultService.addDigitalInvestResult(evidenceId, dto))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_DIGITAL_RESULT')")
    @Operation(summary = "Get all digital investigation results")
    public ApiResponse<Page<DigitalInvestResultDTO>> getAllDigitalInvestResults(@Valid @PathVariable String evidenceId,
                                                                                @RequestParam(defaultValue = "0", required = false) int page,
                                                                                @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DigitalInvestResultDTO> result = digitalInvestResultService.getAllDigitalInvestByEvidenceId(evidenceId, pageable);

        return ApiResponse.<Page<DigitalInvestResultDTO>>builder()
                .code(200)
                .message("Fetched digital investigation results")
                .data(result)
                .build();
    }

    @GetMapping("/{resultId}")
    @PreAuthorize("hasAuthority('VIEW_DIGITAL_RESULT')")
    @Operation(summary = "Get digital investigation result by resultId")
    public ApiResponse<DigitalInvestResultDTO> getDigitalInvestResultByResultId(@Valid @PathVariable String resultId) {
        return ApiResponse.<DigitalInvestResultDTO>builder()
                .code(200)
                .message("Fetched digital investigation result")
                .data(digitalInvestResultService.getDigitalInvestByResultId(resultId))
                .build();
    }

    @PutMapping("/{resultId}")
    @PreAuthorize("hasAuthority('EDIT_DIGITAL_RESULT')")
    @Operation(summary = "Update digital investigation result")
    public ApiResponse<DigitalInvestResultDTO> updateDigitalInvestResult(@Valid @PathVariable String evidenceId, @PathVariable String resultId, @RequestBody DigitalInvestResultDTO dto) {
        return ApiResponse.<DigitalInvestResultDTO>builder()
                .code(200)
                .message("Digital investigation result updated")
                .data(digitalInvestResultService.updateDigitalInvest(evidenceId, resultId, dto))
                .build();
    }

    @DeleteMapping("/{resultId}")
    @PreAuthorize("hasAuthority('DELETE_DIGITAL_RESULT')")
    @Operation(summary = "Delete digital investigation result")
    public ApiResponse<Void> deleteDigitalInvestResult(@Valid @PathVariable String resultId) {
        digitalInvestResultService.deleteDigitalInvestByResultId(resultId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Digital investigation result deleted")
                .build();
    }
}
