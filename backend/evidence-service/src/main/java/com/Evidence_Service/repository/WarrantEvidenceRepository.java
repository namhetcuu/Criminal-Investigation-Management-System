package com.Evidence_Service.repository;

import com.Evidence_Service.entity.WarrantEvidence;
import com.Evidence_Service.entity.id.WarrantEvidenceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarrantEvidenceRepository extends JpaRepository<WarrantEvidence, WarrantEvidenceId> {
    List<WarrantEvidence> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);
    boolean existsByWarrantIdAndIsDeletedFalse(String warrantId);
    List<WarrantEvidence> findAllByWarrantIdAndIsDeletedFalse(String warrantId);
}
