package com.backend.investigationservice.service.serviceImpl;

import com.backend.investigationservice.dto.request.InvestigationPlanCreationRequest;
import com.backend.investigationservice.dto.response.InvestigationPlanResponse;
import com.backend.investigationservice.mapper.InvestigationPlanMapper;
import com.backend.investigationservice.entity.InvestigationPlan;
import com.backend.investigationservice.repository.InvestigationPlanRepository;
import com.backend.investigationservice.service.InvestigationPlanService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@Transactional
public class InvestigationPlanServiceImpl implements InvestigationPlanService {
    private final InvestigationPlanRepository investigationPlanRepository;

    public InvestigationPlanServiceImpl(InvestigationPlanRepository investigationPlanRepository){
        this.investigationPlanRepository = investigationPlanRepository;
    }

    @Override
    public List<InvestigationPlanResponse> findAll() {
        var plans = investigationPlanRepository.findByIsDeletedFalse();
        return plans.stream()
                .map(InvestigationPlanMapper::toResponse)
                .toList();
    }

    @Override
    public Page<InvestigationPlanResponse> findAll(String keyword, Pageable pageable) {
        Specification<InvestigationPlan> specification = (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            Predicate predicate = cb.like(cb.lower(root.get("status")), "%" + keyword.toLowerCase() + "%");
            return cb.and(predicate, cb.isFalse(root.get("isDeleted")));
        };

        return investigationPlanRepository.findAll(specification, pageable)
                .map(InvestigationPlanMapper::toResponse);
    }

    @Override
    public InvestigationPlanResponse createPlan(InvestigationPlanCreationRequest request) {
        if (request == null) throw new IllegalArgumentException("Request cannot be null");

        var entity = InvestigationPlanMapper.toEntity(request);
        var saved = investigationPlanRepository.save(entity);
        return InvestigationPlanMapper.toResponse(saved);
    }

    @Override
    public List<InvestigationPlanResponse> getByCaseId(UUID caseId) {
        return investigationPlanRepository.findByCaseIdAndIsDeletedFalse(caseId).stream()
                .map(InvestigationPlanMapper::toResponse)
                .toList();
    }

    @Override
    public Page<InvestigationPlanResponse> getByCaseId(UUID caseId, Pageable pageable) {
        return investigationPlanRepository
                .findByCaseIdAndIsDeletedFalse(caseId, pageable)
                .map(InvestigationPlanMapper::toResponse);
    }

    @Override
    public InvestigationPlanResponse updatePlan(UUID id, InvestigationPlanCreationRequest request) {
        var plan = investigationPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Investigation plan not found"));

        plan.setSummary(request.getSummary());
        plan.setCreateAt(request.getCreateAt());
        plan.setDeadlineDate(request.getDeadlineDate());
        plan.setStatus(request.getStatus());
        plan.setPlanContent(request.getPlanContent());
        plan.setType(request.getType());
        plan.setHolidayConflict(request.getHolidayConflict());
        plan.setCreatedOfficerName(request.getCreatedOfficerName());
        plan.setAcceptedOfficerName(request.getAcceptedOfficerName());
        plan.setCaseId(UUID.fromString(request.getCaseId()));

        return InvestigationPlanMapper.toResponse(investigationPlanRepository.save(plan));
    }

    @Override
    public InvestigationPlanResponse deletePlan(UUID id) {
        var plan = investigationPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Investigation plan not found"));

        plan.setDeleted(true);
        return InvestigationPlanMapper.toResponse(investigationPlanRepository.save(plan));
    }

}
