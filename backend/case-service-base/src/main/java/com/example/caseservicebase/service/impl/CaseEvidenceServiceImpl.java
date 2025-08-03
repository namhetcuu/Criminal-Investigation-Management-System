/*
 * @ (#) CaseEvidenceServiceImpl.java 1.0 7/10/2025
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

import com.example.caseservicebase.dto.requestDTO.CaseEvidenceRequestDTO;
import com.example.caseservicebase.model.CaseEvidence;
import com.example.caseservicebase.repository.CaseEvidenceRepository;
import com.example.caseservicebase.repository.CaseRepository;
import com.example.caseservicebase.service.CaseEvidenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CaseEvidenceServiceImpl implements CaseEvidenceService {

    private final CaseEvidenceRepository caseEvidenceRepository;
    private final CaseRepository caseRepository;

    @Override
    public Long createCaseEvidence(CaseEvidenceRequestDTO request) {
        if (request.getEvidenceId() == null || request.getEvidenceId() <= 0) {
            throw new IllegalArgumentException("Evidence ID must be a positive non-null value");
        }
        if (request.getCaseId() != null && (request.getCaseId() <= 0 || caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId()).isEmpty())) {
            throw new IllegalArgumentException("Invalid or non-existent Case ID");
        }
        CaseEvidence caseEvidence = CaseEvidence.builder()
                .evidenceId(request.getEvidenceId())
                .caseId(request.getCaseId() != null ? caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId())
                        .orElseThrow(() -> new RuntimeException("Case not found with id = " + request.getCaseId())) : null)
                .isDeleted(false)
                .build();
        log.info("Created case evidence successfully, evidenceId={}", caseEvidence.getEvidenceId());
        return caseEvidenceRepository.save(caseEvidence).getEvidenceId();
    }

    @Override
    public CaseEvidence updateCaseEvidence(Long evidenceId, CaseEvidenceRequestDTO request) {
        if (evidenceId == null || evidenceId <= 0) {
            throw new IllegalArgumentException("Evidence ID must be a positive non-null value");
        }
        if (request.getCaseId() != null && (request.getCaseId() <= 0 || caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId()).isEmpty())) {
            throw new IllegalArgumentException("Invalid or non-existent Case ID");
        }
        CaseEvidence caseEvidence = caseEvidenceRepository.findByEvidenceIdAndIsDeletedFalse(evidenceId)
                .orElseThrow(() -> new RuntimeException("Case Evidence not found with id = " + evidenceId));
        if (request.getCaseId() != null) {
            caseEvidence.setCaseId(caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId())
                    .orElseThrow(() -> new RuntimeException("Case not found with id = " + request.getCaseId())));
        }
        log.info("Updated case evidence successfully, evidenceId={}", evidenceId);
        return caseEvidenceRepository.save(caseEvidence);
    }

    @Override
    public CaseEvidence getCaseEvidenceById(Long evidenceId) {
        if (evidenceId == null || evidenceId <= 0) {
            throw new IllegalArgumentException("Evidence ID must be a positive non-null value");
        }
        return caseEvidenceRepository.findByEvidenceIdAndIsDeletedFalse(evidenceId)
                .orElseThrow(() -> new RuntimeException("Case Evidence not found with id = " + evidenceId));
    }

    @Override
    public List<CaseEvidence> getAllCaseEvidences() {
        return caseEvidenceRepository.findAllByIsDeletedFalse();
    }

    @Override
    public void softDeleteCaseEvidence(Long evidenceId) {
        if (evidenceId == null || evidenceId <= 0) {
            throw new IllegalArgumentException("Evidence ID must be a positive non-null value");
        }
        CaseEvidence caseEvidence = caseEvidenceRepository.findByEvidenceIdAndIsDeletedFalse(evidenceId)
                .orElseThrow(() -> new RuntimeException("Case Evidence not found with id = " + evidenceId));
        caseEvidence.setIsDeleted(true);
        caseEvidenceRepository.save(caseEvidence);
        log.info("Soft deleted case evidence successfully, evidenceId={}", evidenceId);
    }
}