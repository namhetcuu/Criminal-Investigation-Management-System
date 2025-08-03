package com.Evidence_Service.repository;

import com.Evidence_Service.entity.ForensicInvestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForensicInvestResultRepository extends JpaRepository<ForensicInvestResult, String> {
    Optional<ForensicInvestResult> findByResultIdAndIsDeletedFalse(String resultId);
    Page<ForensicInvestResult> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId, Pageable pageable);

    List<ForensicInvestResult> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);

    boolean existsByEvidenceIdAndIsDeletedFalse(String evidenceId);
}
