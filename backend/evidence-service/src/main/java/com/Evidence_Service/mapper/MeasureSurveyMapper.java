package com.Evidence_Service.mapper;

import com.Evidence_Service.dto.MeasureSurveyDTO;
import com.Evidence_Service.entity.MeasureSurvey;
import org.springframework.stereotype.Component;

@Component
public class MeasureSurveyMapper {

    public MeasureSurveyDTO toDTO(MeasureSurvey entity) {
        if (entity == null) return null;

        return MeasureSurveyDTO.builder()
                .resultId(entity.getResultId())
                .measureSurveyId(entity.getMeasureSurveyId())
                .source(entity.getSource())
                .typeName(entity.getTypeName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .isDeleted(entity.isDeleted())
                .build();
    }

    public MeasureSurvey toEntity(MeasureSurveyDTO dto) {
        if (dto == null) return null;

        return MeasureSurvey.builder()
                .resultId(dto.getResultId())
                .measureSurveyId(dto.getMeasureSurveyId())
                .source(dto.getSource())
                .typeName(dto.getTypeName())
                .updatedAt(dto.getUpdatedAt())
                .createdAt(dto.getCreatedAt())
                .isDeleted(dto.isDeleted())
                .build();
    }
}
