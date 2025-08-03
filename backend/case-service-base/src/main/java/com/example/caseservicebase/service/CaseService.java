package com.example.caseservicebase.service;

import com.example.caseservicebase.dto.requestDTO.CaseRequestDTO;
import com.example.caseservicebase.dto.shared.ReportDto;
import com.example.caseservicebase.model.Case;

import java.util.List;

public interface CaseService {
    Long createCase(CaseRequestDTO request);
    Case createCaseFromReport(ReportDto reportDto);
    Case updateCase(Long caseId, CaseRequestDTO request);
    Case getCaseById(Long caseId);
    List<Case> getAllCases();
    void softDeleteCase(Long caseId);
}
