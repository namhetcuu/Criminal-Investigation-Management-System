package com.Evidence_Service.repository;

import com.Evidence_Service.entity.FinancialInvestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialInvestResultRepository extends JpaRepository<FinancialInvestResult, String> {
    Page<FinancialInvestResult> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId, Pageable pageable);
    FinancialInvestResult findByResultIdAndIsDeletedFalse(String resultId);

    List<FinancialInvestResult> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);

    boolean existsByEvidenceIdAndIsDeletedFalse(String evidenceId);
}
