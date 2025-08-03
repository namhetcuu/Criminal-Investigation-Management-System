package com.Evidence_Service.mapper;

import com.Evidence_Service.dto.PhysicalInvestResultDTO;
import com.Evidence_Service.entity.PhysicalInvestResult;
import com.Evidence_Service.entity.PhysicalInvestStatus;

public class PhysicalInvestResultMapper {

    public static PhysicalInvestResultDTO toDTO(PhysicalInvestResult entity) {
        if (entity == null) return null;

        return PhysicalInvestResultDTO.builder()
                .resultId(entity.getResultId())
                .evidenceId(entity.getEvidenceId())
                .status(String.valueOf(entity.getStatus()))
                .notes(entity.getNotes())
                .imageUrl(entity.getImageUrl())
                .createdAt(entity.getCreatedAt())
                .isDeleted(entity.isDeleted())
                .build();
    }

    public static PhysicalInvestResult toEntity(PhysicalInvestResultDTO dto) {
        if (dto == null) return null;

        return PhysicalInvestResult.builder()
                .resultId(dto.getResultId())
                .evidenceId(dto.getEvidenceId())
                .status(PhysicalInvestStatus.valueOf(dto.getStatus()))
                .notes(dto.getNotes())
                .imageUrl(dto.getImageUrl())
                .createdAt(dto.getCreatedAt())
                .isDeleted(dto.isDeleted())
                .build();
    }
}
