package com.Evidence_Service.repository;

import com.Evidence_Service.entity.PhysicalInvestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysicalInvestResultRepository extends JpaRepository<PhysicalInvestResult, String> {
    Page<PhysicalInvestResult> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId, Pageable pageable);
    boolean existsByResultIdAndIsDeletedFalse(String resultId);

    List<PhysicalInvestResult> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);
    PhysicalInvestResult findByResultIdAndIsDeletedFalse(String resultId);

    boolean existsByEvidenceIdAndIsDeletedFalse(String evidenceId);
}
