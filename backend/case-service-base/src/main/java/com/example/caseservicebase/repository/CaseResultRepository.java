package com.example.caseservicebase.repository;

import com.example.caseservicebase.model.Case;
import com.example.caseservicebase.model.CaseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseResultRepository extends JpaRepository<CaseResult, Long> {
    Optional<CaseResult> findByCaseResultIdAndIsDeletedFalse(Long caseResultId);
    List<CaseResult> findAllByIsDeletedFalse();
}