package com.Evidence_Service.repository;

import com.Evidence_Service.entity.MeasureSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureSurveyRepository extends JpaRepository<MeasureSurvey, String> {
    MeasureSurvey findByMeasureSurveyIdAndIsDeletedFalse(String measureSurveyId);
    Page<MeasureSurvey> findAllByIsDeletedFalse(Pageable pageable);
    boolean existsByMeasureSurveyIdAndIsDeletedFalse(String measureSurveyId);
}
