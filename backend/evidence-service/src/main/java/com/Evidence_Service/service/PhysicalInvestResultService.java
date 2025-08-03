package com.Evidence_Service.service;

import com.Evidence_Service.dto.PhysicalInvestResultDTO;
import com.Evidence_Service.event.listener.ResultInvestAssignedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhysicalInvestResultService {
    PhysicalInvestResultDTO addPhysicalInvestResult(String evidenceId, PhysicalInvestResultDTO dto);

    PhysicalInvestResultDTO getPhysicalInvestByResultId(String resultId);

    Page<PhysicalInvestResultDTO> getAllPhysicalInvestByEvidenceId(String evidenceId, Pageable pageable);
    Page<PhysicalInvestResultDTO> getAllPhysicalInvestByInvestigationId(String investigationId, Pageable pageable);

    PhysicalInvestResultDTO updatePhysicalInvest(String evidenceId, String resultId, PhysicalInvestResultDTO dto);

    void deletePhysicalInvestByResultId(String resultId);

    boolean existsByEvidenceId(String evidenceId);

    void deleteByEvidenceId(String evidenceId);

    void assignPhysicalInvestResult(ResultInvestAssignedEvent resultInvestAssignedEvent);
}
