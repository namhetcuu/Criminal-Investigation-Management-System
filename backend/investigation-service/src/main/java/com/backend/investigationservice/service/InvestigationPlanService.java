package com.backend.investigationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.backend.investigationservice.dto.request.InvestigationPlanCreationRequest;
import com.backend.investigationservice.dto.response.InvestigationPlanResponse;

import java.util.List;
import java.util.UUID;

public interface InvestigationPlanService {
    /**
     * Get all investigation plans (no filter, no pagination).
     */
    List<InvestigationPlanResponse> findAll();

    /**
     * Get all plans with optional keyword search and pagination.
     */
    Page<InvestigationPlanResponse> findAll(String keyword, Pageable pageable);

    /**
     * Create a new investigation plan.
     */
    InvestigationPlanResponse createPlan(InvestigationPlanCreationRequest request);

    /**
     * Get all investigation plans by case ID where isDeleted is false.
     */
    List<InvestigationPlanResponse> getByCaseId(UUID caseId);

    /**
     * Get all investigation plans by case ID where isDeleted is false has paging.
     */
    Page<InvestigationPlanResponse> getByCaseId(UUID caseId, Pageable pageable);

    /**
     * Update an existing investigation plan by ID.
     */
    InvestigationPlanResponse updatePlan(UUID id, InvestigationPlanCreationRequest request);

    /**
     * Soft delete (mark isDeleted = true) for a plan by ID.
     */
    InvestigationPlanResponse deletePlan(UUID id);
}
