/*
 * @ (#) CaseArrestServiceImpl.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package com.example.caseservicebase.service.impl;

import com.example.caseservicebase.dto.requestDTO.CaseArrestRequestDTO;
import com.example.caseservicebase.model.CaseArrest;
import com.example.caseservicebase.repository.CaseArrestRepository;
import com.example.caseservicebase.repository.CaseRepository;
import com.example.caseservicebase.service.CaseArrestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/10/2025
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CaseArrestServiceImpl implements CaseArrestService {

    private final CaseArrestRepository caseArrestRepository;
    private final CaseRepository caseRepository;

    @Override
    public Long createCaseArrest(CaseArrestRequestDTO request) {
        if (request.getArrestId() == null || request.getArrestId() <= 0) {
            throw new IllegalArgumentException("Arrest ID must be a positive non-null value");
        }
        if (request.getCaseId() != null && (request.getCaseId() <= 0 || caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId()).isEmpty())) {
            throw new IllegalArgumentException("Invalid or non-existent Case ID");
        }
        if (request.getSuspectId() != null && request.getSuspectId() <= 0) {
            throw new IllegalArgumentException("Suspect ID must be a positive value if provided");
        }
        CaseArrest caseArrest = CaseArrest.builder()
                .arrestId(request.getArrestId())
                .caseId(request.getCaseId() != null ? caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId())
                        .orElseThrow(() -> new RuntimeException("Case not found with id = " + request.getCaseId())) : null)
                .suspectId(request.getSuspectId())
                .isDeleted(false)
                .build();
        log.info("Created case arrest successfully, arrestId={}", caseArrest.getArrestId());
        return caseArrestRepository.save(caseArrest).getArrestId();
    }

    @Override
    public CaseArrest updateCaseArrest(Long arrestId, CaseArrestRequestDTO request) {
        if (arrestId == null || arrestId <= 0) {
            throw new IllegalArgumentException("Arrest ID must be a positive non-null value");
        }
        if (request.getCaseId() != null && (request.getCaseId() <= 0 || caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId()).isEmpty())) {
            throw new IllegalArgumentException("Invalid or non-existent Case ID");
        }
        if (request.getSuspectId() != null && request.getSuspectId() <= 0) {
            throw new IllegalArgumentException("Suspect ID must be a positive value if provided");
        }
        CaseArrest caseArrest = caseArrestRepository.findByArrestIdAndIsDeletedFalse(arrestId)
                .orElseThrow(() -> new RuntimeException("Case Arrest not found with id = " + arrestId));
        if (request.getCaseId() != null) {
            caseArrest.setCaseId(caseRepository.findByCaseIdAndIsDeletedFalse(request.getCaseId())
                    .orElseThrow(() -> new RuntimeException("Case not found with id = " + request.getCaseId())));
        }
        if (request.getSuspectId() != null) {
            caseArrest.setSuspectId(request.getSuspectId());
        }
        log.info("Updated case arrest successfully, arrestId={}", arrestId);
        return caseArrestRepository.save(caseArrest);
    }

    @Override
    public CaseArrest getCaseArrestById(Long arrestId) {
        if (arrestId == null || arrestId <= 0) {
            throw new IllegalArgumentException("Arrest ID must be a positive non-null value");
        }
        return caseArrestRepository.findByArrestIdAndIsDeletedFalse(arrestId)
                .orElseThrow(() -> new RuntimeException("Case Arrest not found with id = " + arrestId));
    }

    @Override
    public List<CaseArrest> getAllCaseArrests() {
        return caseArrestRepository.findAllByIsDeletedFalse();
    }

    @Override
    public void softDeleteCaseArrest(Long arrestId) {
        if (arrestId == null || arrestId <= 0) {
            throw new IllegalArgumentException("Arrest ID must be a positive non-null value");
        }
        CaseArrest caseArrest = caseArrestRepository.findByArrestIdAndIsDeletedFalse(arrestId)
                .orElseThrow(() -> new RuntimeException("Case Arrest not found with id = " + arrestId));
        caseArrest.setIsDeleted(true);
        caseArrestRepository.save(caseArrest);
        log.info("Soft deleted case arrest successfully, arrestId={}", arrestId);
    }
}