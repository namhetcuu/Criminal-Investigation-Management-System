/*
 * @ (#) CaseResultServiceImpl.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package com.example.caseservicebase.service.impl;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/10/2025
 * @version 1.0
 */

import com.example.caseservicebase.dto.requestDTO.CaseResultRequestDTO;
import com.example.caseservicebase.model.CaseResult;
import com.example.caseservicebase.repository.CaseRepository;
import com.example.caseservicebase.repository.CaseResultRepository;
import com.example.caseservicebase.service.CaseResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CaseResultServiceImpl implements CaseResultService {

    private final CaseResultRepository caseResultRepository;
    private final CaseRepository caseRepository;

    @Override
    public Long createCaseResult(CaseResultRequestDTO request) {
        if (request.getCaseResultId() == null || request.getCaseResultId() <= 0) {
            throw new IllegalArgumentException("Case Result ID must be a positive non-null value");
        }
        if (request.getCaseId() != null && (request.getCaseId() <= 0 || caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId()).isEmpty())) {
            throw new IllegalArgumentException("Invalid or non-existent Case ID");
        }
        CaseResult caseResult = CaseResult.builder()
                .caseResultId(request.getCaseResultId())
                .caseId(request.getCaseId() != null ? caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId())
                        .orElseThrow(() -> new RuntimeException("Case not found with id = " + request.getCaseId())) : null)
                .reportTime(request.getReportTime() != null ? LocalDateTime.parse(request.getReportTime()) : null)
                .reportAnalyst(request.getReportAnalyst())
                .summary(request.getSummary())
                .identifyMotive(request.getIdentifyMotive())
                .status(request.getStatus())
                .isDeleted(false)
                .build();
        log.info("Created case result successfully, caseResultId={}", caseResult.getCaseResultId());
        return caseResultRepository.save(caseResult).getCaseResultId();
    }

    @Override
    public CaseResult updateCaseResult(Long caseResultId, CaseResultRequestDTO request) {
        if (caseResultId == null || caseResultId <= 0) {
            throw new IllegalArgumentException("Case Result ID must be a positive non-null value");
        }
        if (request.getCaseId() != null && (request.getCaseId() <= 0 || caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId()).isEmpty())) {
            throw new IllegalArgumentException("Invalid or non-existent Case ID");
        }
        CaseResult caseResult = caseResultRepository.findByCaseResultIdAndIsDeletedFalse(caseResultId)
                .orElseThrow(() -> new RuntimeException("Case Result not found with id = " + caseResultId));
        if (request.getCaseId() != null) {
            caseResult.setCaseId(caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId())
                    .orElseThrow(() -> new RuntimeException("Case not found with id = " + request.getCaseId())));
        }
        if (request.getReportTime() != null) {
            caseResult.setReportTime(LocalDateTime.parse(request.getReportTime()));
        }
        if (request.getReportAnalyst() != null) {
            caseResult.setReportAnalyst(request.getReportAnalyst());
        }
        if (request.getSummary() != null) {
            caseResult.setSummary(request.getSummary());
        }
        if (request.getIdentifyMotive() != null) {
            caseResult.setIdentifyMotive(request.getIdentifyMotive());
        }
        if (request.getStatus() != null) {
            caseResult.setStatus(request.getStatus());
        }
        log.info("Updated case result successfully, caseResultId={}", caseResultId);
        return caseResultRepository.save(caseResult);
    }

    @Override
    public CaseResult getCaseResultById(Long caseResultId) {
        if (caseResultId == null || caseResultId <= 0) {
            throw new IllegalArgumentException("Case Result ID must be a positive non-null value");
        }
        return caseResultRepository.findByCaseResultIdAndIsDeletedFalse(caseResultId)
                .orElseThrow(() -> new RuntimeException("Case Result not found with id = " + caseResultId));
    }

    @Override
    public List<CaseResult> getAllCaseResults() {
        return caseResultRepository.findAllByIsDeletedFalse();
    }

    @Override
    public void softDeleteCaseResult(Long caseResultId) {
        if (caseResultId == null || caseResultId <= 0) {
            throw new IllegalArgumentException("Case Result ID must be a positive non-null value");
        }
        CaseResult caseResult = caseResultRepository.findByCaseResultIdAndIsDeletedFalse(caseResultId)
                .orElseThrow(() -> new RuntimeException("Case Result not found with id = " + caseResultId));
        caseResult.setIsDeleted(true);
        caseResultRepository.save(caseResult);
        log.info("Soft deleted case result successfully, caseResultId={}", caseResultId);
    }
}