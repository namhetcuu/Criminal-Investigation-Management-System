package com.Evidence_Service.repository;

import com.Evidence_Service.entity.ReportEvidence;
import com.Evidence_Service.entity.id.ReportEvidenceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportEvidenceRepository extends JpaRepository<ReportEvidence, ReportEvidenceId> {

    List<ReportEvidence> findAllByEvidenceId(String evidenceId);

    boolean existsByReportIdAndEvidenceId(String reportId, String evidenceId);

    List<ReportEvidence> findAllByReportIdAndIsDeletedFalse(String reportId);
    boolean existsByReportIdAndIsDeletedFalse(String reportId);
    List<ReportEvidence> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);
    void deleteByReportIdAndEvidenceId(String reportId, String evidenceId);
}
