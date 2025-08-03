package com.Evidence_Service.service;

import com.Evidence_Service.dto.*;
import com.Evidence_Service.event.listener.AnalysisResultEvent;
import com.Evidence_Service.event.caller.EvidenceCreatedEvent;
import com.Evidence_Service.event.listener.ResultInvestAssignedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface EvidenceService {

    EvidenceDTO createEvidence(EvidenceDTO dto);

    EvidenceDTO getByEvidenceId(String evidenceId);
    List<EvidenceDTO> getByEvidenceIds(List<String> evidenceIds);

    void assignInvestResultByInvestigationPlanId(ResultInvestAssignedEvent event);

    void deleteInvestResultByInvestigationPlanId(String investigationPlanId, String type) ;

    EvidenceDTO updateEvidence(EvidenceDTO dto);

    void deleteByEvidenceId(String evidenceId);
    void deleteByInvestigationPlanId(String investigationPlanId);


    EvidenceDTO assignCase(String evidenceId, CaseDTO dto);
    EvidenceDTO assignSuspect(String evidenceId, SuspectDTO dto);
    EvidenceDTO assignWarrant(String evidenceId, WarrantDTO dto);
    List<SuspectDTO> getSuspectsByEvidence(String evidenceId);
    List<WarrantDTO> getWarrantsByEvidence(String evidenceId);
    List<CaseDTO> getCasesByEvidence(String evidenceId);
    List<ReportDTO> getReportsByEvidence(String evidenceId);


    void removeSuspectFromEvidence(String suspectId);
    void saveEvidenceFromEvent(EvidenceCreatedEvent event);
    void updateAnalysisResult(AnalysisResultEvent event);
    void assignSuspectToEvidence(String evidenceId, String suspectId);
    void assignCaseToEvidence(String evidenceId, String caseId);
    void assignWarrantToEvidence(String evidenceId, String caseId);

    void assignReportToEvidence(String evidenceId, String caseId);


    boolean existsByReportId(String reportId);

    boolean existsByCaseId(String caseId);

    boolean existsBySuspectId(String suspectId);

    boolean existsByWarrantId(String warrantId);

    void softDeleteByMeasureSurveyId(String measureSurveyId);

    boolean existsByEvidenceId(String evidenceId);

    void deleteByReportId(String reportId);

    void deleteByCaseId(String caseId);

    void deleteByWarrantId(String warrantId);

    void deleteBySuspectId(String suspectId);

    Page<EvidenceDTO> getAllEvidence(Pageable pageable);
}