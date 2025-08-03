/*
 * @ (#) CaseArrestService.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */
package com.example.caseservicebase.service;

import com.example.caseservicebase.dto.requestDTO.CaseArrestRequestDTO;
import com.example.caseservicebase.model.CaseArrest;

import java.util.List;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/10/2025
 * @version 1.0
 */
public interface CaseArrestService {
    Long createCaseArrest(CaseArrestRequestDTO request);
    CaseArrest updateCaseArrest(Long arrestId, CaseArrestRequestDTO request);
    CaseArrest getCaseArrestById(Long arrestId);
    List<CaseArrest> getAllCaseArrests();
    void softDeleteCaseArrest(Long arrestId);
}