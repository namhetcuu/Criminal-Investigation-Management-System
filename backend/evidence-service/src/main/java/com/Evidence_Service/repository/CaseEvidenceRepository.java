package com.Evidence_Service.repository;

import com.Evidence_Service.entity.CaseEvidence;
import com.Evidence_Service.entity.id.CaseEvidenceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseEvidenceRepository extends JpaRepository<CaseEvidence, CaseEvidenceId> {
    List<CaseEvidence> findAllByCaseIdAndIsDeletedFalse(String caseId);
    List<CaseEvidence> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);
    boolean existsByCaseIdAndIsDeletedFalse(String caseId);
}
