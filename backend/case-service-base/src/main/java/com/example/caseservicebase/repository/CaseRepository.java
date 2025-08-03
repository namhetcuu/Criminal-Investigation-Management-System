package com.example.caseservicebase.repository;

import com.example.caseservicebase.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
    Optional<Case> findByCaseIdAndIsDeletedFalse(Long caseId);
    List<Case> findAllByIsDeletedFalse();
}

