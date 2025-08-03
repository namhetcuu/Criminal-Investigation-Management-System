/*
 * @ (#) CaseResultController.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package com.example.caseservicebase.controller;

import com.example.caseservicebase.dto.requestDTO.CaseResultRequestDTO;
import com.example.caseservicebase.dto.responseDTO.ResponseData;
import com.example.caseservicebase.exception.InvalidRequestException;
import com.example.caseservicebase.exception.ResourceNotFoundException;
import com.example.caseservicebase.model.CaseResult;
import com.example.caseservicebase.service.CaseResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description Controller xử lý các yêu cầu liên quan đến entity CaseResult.
 * @author Nguyen Truong An
 * @date 7/10/2025
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/case/result")
public class CaseResultController {

    private final CaseResultService caseResultService;

    @PostMapping("")
    public ResponseData<Long> createCaseResult(@RequestBody CaseResultRequestDTO request) {
        log.info("Request to create case result, caseResultId={}", request.getCaseResultId());
        try {
            if (request.getCaseResultId() == null) {
                throw new InvalidRequestException("Case Result ID cannot be null");
            }
            if (request.getCaseId() != null && request.getCaseId() <= 0) {
                throw new InvalidRequestException("Case ID must be a positive value if provided");
            }
            Long caseResultId = caseResultService.createCaseResult(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Case result created successfully", caseResultId);
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to create case result due to invalid request");
        }
    }

    @PutMapping("/{caseResultId}")
    public ResponseData<CaseResult> updateCaseResult(@PathVariable Long caseResultId, @RequestBody CaseResultRequestDTO request) {
        log.info("Request to update case result, caseResultId={}", caseResultId);
        try {
            if (caseResultId == null) {
                throw new InvalidRequestException("Case Result ID cannot be null");
            }
            if (request.getCaseId() != null && request.getCaseId() <= 0) {
                throw new InvalidRequestException("Case ID must be a positive value if provided");
            }
            CaseResult updatedCaseResult = caseResultService.updateCaseResult(caseResultId, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Case result updated successfully", updatedCaseResult);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to update case result due to invalid request");
        }
    }

    @GetMapping("/{caseResultId}")
    public ResponseData<CaseResult> getCaseResultById(@PathVariable Long caseResultId) {
        log.info("Request to retrieve case result, caseResultId={}", caseResultId);
        try {
            if (caseResultId == null) {
                throw new InvalidRequestException("Case Result ID cannot be null");
            }
            CaseResult caseResult = caseResultService.getCaseResultById(caseResultId);
            return new ResponseData<>(HttpStatus.OK.value(), "Case result retrieved successfully", caseResult);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to retrieve case result due to invalid request");
        }
    }

    @GetMapping("")
    public ResponseData<List<CaseResult>> getAllCaseResults() {
        log.info("Request to retrieve all case results");
        try {
            List<CaseResult> caseResults = caseResultService.getAllCaseResults();
            return new ResponseData<>(HttpStatus.OK.value(), "Case results retrieved successfully", caseResults);
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to retrieve case results due to an error");
        }
    }

    @DeleteMapping("/{caseResultId}")
    public ResponseData<Void> softDeleteCaseResult(@PathVariable Long caseResultId) {
        log.info("Request to soft delete case result, caseResultId={}", caseResultId);
        try {
            if (caseResultId == null) {
                throw new InvalidRequestException("Case Result ID cannot be null");
            }
            caseResultService.softDeleteCaseResult(caseResultId);
            return new ResponseData<>(HttpStatus.OK.value(), "Case result soft deleted successfully", null);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to soft delete case result due to invalid request");
        }
    }
}