package com.Evidence_Service.mapper;

import com.Evidence_Service.dto.FinancialInvestResultDTO;
import com.Evidence_Service.entity.FinancialInvestResult;

public class FinancialInvestResultMapper {

    public static FinancialInvestResultDTO toDTO(FinancialInvestResult entity) {
        if (entity == null) return null;

        return FinancialInvestResultDTO.builder()
                .resultId(entity.getResultId())
                .evidenceId(entity.getEvidenceId())
                .summary(entity.getSummary())
                .attachedFile(entity.getAttachedFile())
                .createdAt(entity.getCreatedAt())
                .isDeleted(entity.isDeleted())
                .build();
    }

    public static FinancialInvestResult toEntity(FinancialInvestResultDTO dto) {
        if (dto == null) return null;

        return FinancialInvestResult.builder()
                .resultId(dto.getResultId())
                .evidenceId(dto.getEvidenceId())
                .summary(dto.getSummary())
                .attachedFile(dto.getAttachedFile())
                .createdAt(dto.getCreatedAt())
                .isDeleted(dto.isDeleted())
                .build();
    }
}
