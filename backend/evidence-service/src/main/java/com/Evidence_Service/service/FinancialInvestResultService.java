package com.Evidence_Service.service;

import com.Evidence_Service.dto.FinancialInvestResultDTO;
import com.Evidence_Service.event.listener.ResultInvestAssignedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FinancialInvestResultService {
    FinancialInvestResultDTO addFinancialInvestResult(String evidenceId, FinancialInvestResultDTO dto);

    FinancialInvestResultDTO getFinancialInvestById(String resultId);

    void assignFinancialInvestResult(ResultInvestAssignedEvent resultInvestAssignedEvent);


    Page<FinancialInvestResultDTO> getAllFinancialInvestByEvidenceId(String evidenceId, Pageable pageable);

    FinancialInvestResultDTO updateFinancialInvest(String evidenceId, String resultId, FinancialInvestResultDTO dto);

    void deleteFinancialInvestByResultId(String resultId);

    boolean existsByEvidenceId(String evidenceId);

    void deleteByEvidenceId(String evidenceId);
}
