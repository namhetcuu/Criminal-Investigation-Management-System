package com.backend.investigationservice.service;

public interface InvestigationResultService {

    void saveDigitalResult(String result);
    void updateDigitalResult(String result);

    void savePhysicalResult(String result);
    void updatePhysicalResult(String result);

    void saveFinancialResult(String result);
    void updateFinancialResult(String result);

    void saveForensicResult(String result);
    void updateForensicResult(String result);
}