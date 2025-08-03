/*
 * @ (#) CaseEvidenceController.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package com.example.caseservicebase.controller;

import com.example.caseservicebase.dto.requestDTO.CaseEvidenceRequestDTO;
import com.example.caseservicebase.dto.responseDTO.ResponseData;
import com.example.caseservicebase.exception.InvalidRequestException;
import com.example.caseservicebase.exception.ResourceNotFoundException;
import com.example.caseservicebase.model.CaseEvidence;
import com.example.caseservicebase.service.CaseEvidenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description Controller xử lý các yêu cầu liên quan đến entity CaseEvidence.
 * @author Nguyen Truong An
 * @date 7/10/2025
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/case/evidence")
public class CaseEvidenceController {

    private final CaseEvidenceService caseEvidenceService;

    @PostMapping("")
    public ResponseData<Long> createCaseEvidence(@RequestBody CaseEvidenceRequestDTO request) {
        log.info("Request to create case evidence, evidenceId={}", request.getEvidenceId());
        try {
            if (request.getEvidenceId() == null) {
                throw new InvalidRequestException("Evidence ID cannot be null");
            }
            if (request.getCaseId() != null && request.getCaseId() <= 0) {
                throw new InvalidRequestException("Case ID must be a positive value if provided");
            }
            Long evidenceId = caseEvidenceService.createCaseEvidence(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Case evidence created successfully", evidenceId);
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to create case evidence due to invalid request");
        }
    }

    @PutMapping("/{evidenceId}")
    public ResponseData<CaseEvidence> updateCaseEvidence(@PathVariable Long evidenceId, @RequestBody CaseEvidenceRequestDTO request) {
        log.info("Request to update case evidence, evidenceId={}", evidenceId);
        try {
            if (evidenceId == null) {
                throw new InvalidRequestException("Evidence ID cannot be null");
            }
            if (request.getCaseId() != null && request.getCaseId() <= 0) {
                throw new InvalidRequestException("Case ID must be a positive value if provided");
            }
            CaseEvidence updatedCaseEvidence = caseEvidenceService.updateCaseEvidence(evidenceId, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Case evidence updated successfully", updatedCaseEvidence);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to update case evidence due to invalid request");
        }
    }

    @GetMapping("/{evidenceId}")
    public ResponseData<CaseEvidence> getCaseEvidenceById(@PathVariable Long evidenceId) {
        log.info("Request to retrieve case evidence, evidenceId={}", evidenceId);
        try {
            if (evidenceId == null) {
                throw new InvalidRequestException("Evidence ID cannot be null");
            }
            CaseEvidence caseEvidence = caseEvidenceService.getCaseEvidenceById(evidenceId);
            return new ResponseData<>(HttpStatus.OK.value(), "Case evidence retrieved successfully", caseEvidence);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to retrieve case evidence due to invalid request");
        }
    }

    @GetMapping("")
    public ResponseData<List<CaseEvidence>> getAllCaseEvidences() {
        log.info("Request to retrieve all case evidences");
        try {
            List<CaseEvidence> caseEvidences = caseEvidenceService.getAllCaseEvidences();
            return new ResponseData<>(HttpStatus.OK.value(), "Case evidences retrieved successfully", caseEvidences);
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to retrieve case evidences due to an error");
        }
    }

    @DeleteMapping("/{evidenceId}")
    public ResponseData<Void> softDeleteCaseEvidence(@PathVariable Long evidenceId) {
        log.info("Request to soft delete case evidence, evidenceId={}", evidenceId);
        try {
            if (evidenceId == null) {
                throw new InvalidRequestException("Evidence ID cannot be null");
            }
            caseEvidenceService.softDeleteCaseEvidence(evidenceId);
            return new ResponseData<>(HttpStatus.OK.value(), "Case evidence soft deleted successfully", null);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to soft delete case evidence due to invalid request");
        }
    }
}