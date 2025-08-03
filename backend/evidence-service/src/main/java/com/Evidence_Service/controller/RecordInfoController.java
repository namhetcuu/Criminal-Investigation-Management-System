package com.Evidence_Service.controller;

import com.Evidence_Service.dto.RecordInfoDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.RecordInfoService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/v1/record-info")
@Tag(name = "Record Info", description = "Management of images/videos/evidence attachments")
@RequiredArgsConstructor
public class RecordInfoController {
    private final RecordInfoService recordInfoService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_RECORD_INFO')")
    @Operation(summary = "Create new record info")
    public ResponseEntity<ApiResponse<RecordInfoDTO>> createRecordInfo(@Valid @RequestBody RecordInfoDTO dto) {
        ApiResponse<RecordInfoDTO> response = ApiResponse.<RecordInfoDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Record info created")
                .data(recordInfoService.createRecordInfo(dto))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{recordInfoId}")
    @PreAuthorize("hasAuthority('VIEW_RECORD_INFO')")
    @Operation(summary = "Get record info by ID")
    public ResponseEntity<ApiResponse<RecordInfoDTO>> getRecordInfoById(@PathVariable String recordInfoId) {
        ApiResponse<RecordInfoDTO> response = ApiResponse.<RecordInfoDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Fetched record info")
                .data(recordInfoService.getRecordInfoByRecordInfoId(recordInfoId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_RECORD_INFO')")
    @Operation(summary = "Get all record info by evidence ID")
    public ResponseEntity<ApiResponse<Page<RecordInfoDTO>>> getAllRecordInfoByEvidenceId(
            @RequestParam String evidenceId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecordInfoDTO> result = recordInfoService.getRecordInfoByEvidenceId(evidenceId, pageable);
        ApiResponse<Page<RecordInfoDTO>> response = ApiResponse.<Page<RecordInfoDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Fetched all record info")
                .data(result)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{recordInfoId}")
    @PreAuthorize("hasAuthority('EDIT_RECORD_INFO')")
    @Operation(summary = "Update record info")
    public ResponseEntity<ApiResponse<RecordInfoDTO>> updateRecordInfo(
            @PathVariable String recordInfoId, @Valid @RequestBody RecordInfoDTO dto) {
        ApiResponse<RecordInfoDTO> response = ApiResponse.<RecordInfoDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Record info updated")
                .data(recordInfoService.updateRecordInfo(recordInfoId, dto))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{recordInfoId}")
    @PreAuthorize("hasAuthority('DELETE_RECORD_INFO')")
    @Operation(summary = "Delete record info")
    public ResponseEntity<ApiResponse<Void>> deleteRecordInfo(@PathVariable String recordInfoId) {
        recordInfoService.deleteRecordInfoByRecordInfoId(recordInfoId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Record info deleted")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}