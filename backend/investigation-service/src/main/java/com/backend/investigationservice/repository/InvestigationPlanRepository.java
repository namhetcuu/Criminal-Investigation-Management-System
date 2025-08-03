package com.backend.investigationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.investigationservice.entity.InvestigationPlan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestigationPlanRepository extends JpaRepository<InvestigationPlan, UUID>, JpaSpecificationExecutor<InvestigationPlan> {
    List<InvestigationPlan> findByCaseIdAndIsDeletedFalse(UUID caseId);

    List<InvestigationPlan> findByIsDeletedFalse();

    Page<InvestigationPlan> findByCaseIdAndIsDeletedFalse(UUID caseId, Pageable pageable);
}
