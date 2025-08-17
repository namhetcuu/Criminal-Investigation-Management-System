// CaseService.java
package com.example.backend.service;

import com.example.backend.dto.request.CaseRequest;
import com.example.backend.dto.response.CaseResponse;
import java.util.List;

public interface CaseService {
    CaseResponse createCase(CaseRequest request);
    List<CaseResponse> getAllCases();
}
