package com.Evidence_Service.controller;

import com.Evidence_Service.dto.FinancialInvestResultDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.FinancialInvestResultService;
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
@RequestMapping("/api/v1/evidences/{evidenceId}/financial-invest")
@Tag(name = "Financial Investigation", description = "Financial investigation results")
@RequiredArgsConstructor
public class FinancialInvestResultController {
    private final FinancialInvestResultService financialInvestResultService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_FINANCIAL_RESULT')")
    @Operation(summary = "Create financial investigation result")
    public ApiResponse<FinancialInvestResultDTO> createFinancialInvestResult(@Valid  @PathVariable String evidenceId, @RequestBody FinancialInvestResultDTO dto) {
        return ApiResponse.<FinancialInvestResultDTO>builder().code(201).message("Created").data(financialInvestResultService.addFinancialInvestResult(evidenceId, dto)).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_FINANCIAL_RESULT')")
    @Operation(summary = "Get all financial investigation results")
    public ApiResponse<Page<FinancialInvestResultDTO>> getAllFinancialInvestResults(@Valid @PathVariable String evidenceId,
                                                                                    @RequestParam(defaultValue = "0", required = false) int page,
                                                                                    @RequestParam(defaultValue = "10", required = false) int size ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FinancialInvestResultDTO> result = financialInvestResultService.getAllFinancialInvestByEvidenceId(evidenceId, pageable);
        return ApiResponse.<Page<FinancialInvestResultDTO>>builder()
                .code(200)
                .message("Financial Invest Fetched")
                .data(result)
                .build();
    }

    @GetMapping("/{resultId}")
    @PreAuthorize("hasAuthority('VIEW_FINANCIAL_RESULT')")
    @Operation(summary = "Get financial investigation result by resultId")
    public ApiResponse<FinancialInvestResultDTO> getFinancialInvestResultById(@Valid @PathVariable String resultId) {
        return ApiResponse.<FinancialInvestResultDTO>builder().code(200).message("Fetched").data(financialInvestResultService.getFinancialInvestById(resultId)).build();
    }

    @PutMapping("/{resultId}")
    @PreAuthorize("hasAuthority('EDIT_FINANCIAL_RESULT')")
    @Operation(summary = "Update financial investigation result")
    public ApiResponse<FinancialInvestResultDTO> updateFinancialInvestResult(@Valid @PathVariable String evidenceId, @PathVariable String resultId, @RequestBody FinancialInvestResultDTO dto) {
        return ApiResponse.<FinancialInvestResultDTO>builder().code(200).message("Updated").data(financialInvestResultService.updateFinancialInvest(evidenceId, resultId, dto)).build();
    }

    @DeleteMapping("/{resultId}")
    @PreAuthorize("hasAuthority('DELETE_FINANCIAL_RESULT')")
    @Operation(summary = "Delete financial investigation result")
    public ApiResponse<Void> deleteFinancialInvestResult(@Valid @PathVariable String resultId) {
        financialInvestResultService.deleteFinancialInvestByResultId(resultId);
        return ApiResponse.<Void>builder().code(200).message("Deleted").build();
    }
}

