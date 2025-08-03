package com.Evidence_Service.service;

import com.Evidence_Service.dto.MeasureSurveyDTO;
import com.Evidence_Service.dto.RecordInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeasureSurveyService {

    MeasureSurveyDTO createMeasureSurvey(MeasureSurveyDTO dto);

    Page<MeasureSurveyDTO> getAllMeasureSurvey(Pageable pageable);

    MeasureSurveyDTO getMeasureSurveyByMeasureSurveyId(String measureSurveyId);
    
    MeasureSurveyDTO updateMeasureSurvey(String measureSurveyId, MeasureSurveyDTO dto);

    void deleteMeasureSurveyByMeasureSurveyId(String measureSurveyId);
}
