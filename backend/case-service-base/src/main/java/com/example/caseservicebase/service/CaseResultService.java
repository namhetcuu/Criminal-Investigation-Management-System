/*
 * @ (#) CaseResultService.java 1.0 7/10/2025
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
import com.example.caseservicebase.dto.requestDTO.CaseResultRequestDTO;
import com.example.caseservicebase.model.CaseResult;

import java.util.List;

public interface CaseResultService {
    Long createCaseResult(CaseResultRequestDTO request);
    CaseResult updateCaseResult(Long caseResultId, CaseResultRequestDTO request);
    CaseResult getCaseResultById(Long caseResultId);
    List<CaseResult> getAllCaseResults();
    void softDeleteCaseResult(Long caseResultId);
}
