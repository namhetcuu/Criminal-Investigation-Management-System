/*
 * @ (#) CaseEvidenceService.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */
package com.example.caseservicebase.service;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/10/2025
 * @version 1.0
 */
import com.example.caseservicebase.dto.requestDTO.CaseEvidenceRequestDTO;
import com.example.caseservicebase.model.CaseEvidence;

import java.util.List;

public interface CaseEvidenceService {
    Long createCaseEvidence(CaseEvidenceRequestDTO request);
    CaseEvidence updateCaseEvidence(Long evidenceId, CaseEvidenceRequestDTO request);
    CaseEvidence getCaseEvidenceById(Long evidenceId);
    List<CaseEvidence> getAllCaseEvidences();
    void softDeleteCaseEvidence(Long evidenceId);
}