package com.backend.investigationservice.repository;

import com.backend.investigationservice.entity.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, UUID>, JpaSpecificationExecutor<Interview> {
    List<Interview> findByCaseIdAndIsDeletedFalse(UUID caseId);

    Page<Interview> findByCaseIdAndIsDeletedFalse(UUID caseId, Pageable pageable);
}
