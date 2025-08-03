package com.Evidence_Service.repository;

import com.Evidence_Service.entity.SuspectEvidence;
import com.Evidence_Service.entity.id.SuspectEvidenceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuspectEvidenceRepository extends JpaRepository<SuspectEvidence, SuspectEvidenceId> {

    List<SuspectEvidence> findAllByEvidenceId(String evidenceId);

    boolean existsBySuspectIdAndEvidenceId(String suspectId, String evidenceId);
    boolean existsBySuspectIdAndIsDeletedFalse(String suspectId);

    List<SuspectEvidence> findAllBySuspectIdAndIsDeletedFalse(String suspectId);
    List<SuspectEvidence> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);
    void deleteBySuspectIdAndEvidenceId(String suspectId, String evidenceId);
}
