package com.example.caseservicebase.service.impl;

import com.example.caseservicebase.dto.requestDTO.CaseRequestDTO;
import com.example.caseservicebase.dto.shared.ReportDto;
import com.example.caseservicebase.model.Case;
import com.example.caseservicebase.repository.CaseRepository;
import com.example.caseservicebase.service.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;

    @Override
    public Long createCase(CaseRequestDTO request) {
        if (request.getCaseId() == null || request.getCaseId() <= 0) {
            throw new IllegalArgumentException("Case ID must be a positive non-null value");
        }
        Case caseEntity = Case.builder()
                .caseId(request.getCaseId())
                .caseNumber(request.getCaseNumber())
                .caseTarget(request.getCaseTarget())
                .severity(request.getSeverity())
                .status(request.getStatus())
                .summary(request.getSummary())
                .typeCase(request.getTypeCase())
                .isDeleted(false)
                .build();
        log.info("Create case successfully, caseId={}", caseEntity.getCaseId());
        return caseRepository.save(caseEntity).getCaseId();
    }

    @Override
    public Case createCaseFromReport(ReportDto reportDto) {
        Case caseEntity = Case.builder()
                .caseNumber(UUID.randomUUID().toString())
                .typeCase(reportDto.getTypeReport())
                .severity(reportDto.getSeverity())
                .status("New case")
                .isDeleted(false)
                .build();
        return caseRepository.save(caseEntity);
    }

    @Override
    public Case updateCase(Long caseId, CaseRequestDTO request) {
        if (caseId == null || caseId <= 0) {
            throw new IllegalArgumentException("Case ID must be a positive non-null value");
        }
        Case caseEntity = caseRepository.findByCaseIdAndIsDeletedFalse(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found with id = " + caseId));
        if (request.getCaseNumber() != null) caseEntity.setCaseNumber(request.getCaseNumber());
        if (request.getCaseTarget() != null) caseEntity.setCaseTarget(request.getCaseTarget());
        if (request.getSeverity() != null) caseEntity.setSeverity(request.getSeverity());
        if (request.getStatus() != null) caseEntity.setStatus(request.getStatus());
        if (request.getSummary() != null) caseEntity.setSummary(request.getSummary());
        if (request.getTypeCase() != null) caseEntity.setTypeCase(request.getTypeCase());
        log.info("Case has updated successfully, caseId={}", caseId);
        return caseRepository.save(caseEntity);
    }

    @Override
    public Case getCaseById(Long caseId) {
        if (caseId == null || caseId <= 0) {
            throw new IllegalArgumentException("Case ID must be a positive non-null value");
        }
        return caseRepository.findByCaseIdAndIsDeletedFalse(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found with id = " + caseId));
    }

    @Override
    public List<Case> getAllCases() {
        return caseRepository.findAllByIsDeletedFalse();
    }

    @Override
    public void softDeleteCase(Long caseId) {
        if (caseId == null || caseId <= 0) {
            throw new IllegalArgumentException("Case ID must be a positive non-null value");
        }
        Case caseEntity = caseRepository.findByCaseIdAndIsDeletedFalse(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found with id = " + caseId));
        caseEntity.setIsDeleted(true);
        caseRepository.save(caseEntity);
        log.info("Soft deleted case successfully, caseId={}", caseId);
    }

}
