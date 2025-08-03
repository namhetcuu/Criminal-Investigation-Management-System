/*
 * @ (#) CaseController.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package com.example.caseservicebase.controller;

import com.example.caseservicebase.dto.requestDTO.CaseRequestDTO;
import com.example.caseservicebase.dto.responseDTO.ResponseData;
import com.example.caseservicebase.exception.InvalidRequestException;
import com.example.caseservicebase.exception.ResourceNotFoundException;
import com.example.caseservicebase.model.Case;
import com.example.caseservicebase.service.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description Controller xử lý các yêu cầu liên quan đến entity Case.
 * @author Nguyen Truong An
 * @date 7/10/2025
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/case")
public class CaseController {

    private final CaseService caseService;

    @PostMapping("")
    public ResponseData<Long> createCase(@RequestBody CaseRequestDTO request) {
        log.info("Request to create case, caseId={}", request.getCaseId());
        try {
            if (request.getCaseId() == null) {
                throw new InvalidRequestException("Case ID cannot be null");
            }
            Long caseId = caseService.createCase(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Case created successfully", caseId);
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to create case due to invalid request");
        }
    }

    @PutMapping("/{caseId}")
    public ResponseData<Case> updateCase(@PathVariable Long caseId, @RequestBody CaseRequestDTO request) {
        log.info("Request to update case, caseId={}", caseId);
        try {
            if (caseId == null) {
                throw new InvalidRequestException("Case ID cannot be null");
            }
            Case updatedCase = caseService.updateCase(caseId, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Case updated successfully", updatedCase);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to update case due to invalid request");
        }
    }

    @GetMapping("/{caseId}")
    public ResponseData<Case> getCaseById(@PathVariable Long caseId) {
        log.info("Request to retrieve case, caseId={}", caseId);
        try {
            if (caseId == null) {
                throw new InvalidRequestException("Case ID cannot be null");
            }
            Case caseEntity = caseService.getCaseById(caseId);
            return new ResponseData<>(HttpStatus.OK.value(), "Case retrieved successfully", caseEntity);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to retrieve case due to invalid request");
        }
    }

    @GetMapping("")
    public ResponseData<List<Case>> getAllCases() {
        log.info("Request to retrieve all cases");
        try {
            List<Case> cases = caseService.getAllCases();
            return new ResponseData<>(HttpStatus.OK.value(), "Cases retrieved successfully", cases);
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to retrieve cases due to an error");
        }
    }

    @DeleteMapping("/{caseId}")
    public ResponseData<Void> softDeleteCase(@PathVariable Long caseId) {
        log.info("Request to soft delete case, caseId={}", caseId);
        try {
            if (caseId == null) {
                throw new InvalidRequestException("Case ID cannot be null");
            }
            caseService.softDeleteCase(caseId);
            return new ResponseData<>(HttpStatus.OK.value(), "Case soft deleted successfully", null);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to soft delete case due to invalid request");
        }
    }
}