package com.Evidence_Service.repository;

import com.Evidence_Service.entity.Evidence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, String> {
    @Query("SELECT e FROM Evidence e WHERE e.Id IN :ids AND e.isDeleted = false")
    Page<Evidence> findByEvidenceIdInAndNotDeleted(@Param("ids") List<String> ids, Pageable pageable);

    @Query("SELECT e FROM Evidence e WHERE e.isDeleted = false")
    Page<Evidence> findAllNotDeleted(Pageable pageable);


    List<Evidence> findAllByInvestigationPlanIdAndIsDeletedFalse(String investigationPlanId);

    List<Evidence> findByReportIdAndIsDeletedFalse(String reportId);

    List<Evidence> findByCollectorUsernameAndIsDeletedFalse(String collectorUsername);

    List<Evidence> findByMeasureSurveyIdAndIsDeletedFalse(String measureSurveyId);

    List<Evidence> findAllByIsDeletedFalse();

    Optional<Evidence> findByEvidenceIdAndIsDeletedFalse(String evidenceId);

    boolean existsByEvidenceIdAndIsDeletedFalse(String evidenceId);

    List<Evidence> findAllByMeasureSurveyIdAndIsDeletedFalse(String measureSurveyId);

    List<Evidence> findAllByDescriptionContainingIgnoreCaseAndIsDeletedFalse(String keyword);

}
