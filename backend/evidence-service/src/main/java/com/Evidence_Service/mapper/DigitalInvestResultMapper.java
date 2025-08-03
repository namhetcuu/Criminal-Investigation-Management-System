package com.Evidence_Service.mapper;

import com.Evidence_Service.dto.DigitalInvestResultDTO;
import com.Evidence_Service.entity.DigitalInvestResult;

public class DigitalInvestResultMapper {

    public static DigitalInvestResultDTO toDTO(DigitalInvestResult entity) {
        if (entity == null) return null;

        return DigitalInvestResultDTO.builder()
                .resultId(entity.getResultId())
                .evidenceId(entity.getEvidenceId())
                .deviceType(entity.getDeviceType())
                .analystTool(entity.getAnalystTool())
                .result(entity.getResult())
                .createdAt(entity.getCreatedAt())
                .isDeleted(entity.isDeleted())
                .build();
    }

    public static DigitalInvestResult toEntity(DigitalInvestResultDTO dto) {
        if (dto == null) return null;

        return DigitalInvestResult.builder()
                .resultId(dto.getResultId())
                .evidenceId(dto.getEvidenceId())
                .deviceType(dto.getDeviceType())
                .analystTool(dto.getAnalystTool())
                .result(dto.getResult())
                .createdAt(dto.getCreatedAt())
                .isDeleted(dto.isDeleted())
                .build();
    }
}
