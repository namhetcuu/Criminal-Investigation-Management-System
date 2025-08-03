package com.Evidence_Service.repository;

import com.Evidence_Service.entity.DigitalInvestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DigitalInvestResultRepository extends JpaRepository<DigitalInvestResult, String> {
    Page<DigitalInvestResult> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId, Pageable pageable);
    List<DigitalInvestResult> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);
    Optional<DigitalInvestResult> findByResultIdAndIsDeletedFalse(String resultId);
    boolean existsByEvidenceIdAndIsDeletedFalse(String evidenceId);
}
