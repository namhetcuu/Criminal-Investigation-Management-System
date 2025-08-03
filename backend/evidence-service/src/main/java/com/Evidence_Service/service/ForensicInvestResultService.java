package com.Evidence_Service.service;

import com.Evidence_Service.dto.ForensicInvestResultDTO;
import com.Evidence_Service.event.listener.ResultInvestAssignedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ForensicInvestResultService {
    ForensicInvestResultDTO addForensicInvestResult(String evidenceId, ForensicInvestResultDTO dto);

    void assignForensicInvestResult(ResultInvestAssignedEvent resultInvestAssignedEvent);

    ForensicInvestResultDTO getForensicInvestById(String resultId);

    Page<ForensicInvestResultDTO> getAllForensicInvestByEvidenceId(String evidenceId, Pageable pageable);

    ForensicInvestResultDTO updateForensicInvest(String evidenceId, String resultId, ForensicInvestResultDTO dto);

    void deleteForensicInvestByResultId(String resultId);

    boolean existsByEvidenceId(String evidenceId);

    void deleteByEvidenceId(String evidenceId);
}
