package com.Evidence_Service.mapper;

import com.Evidence_Service.dto.RecordInfoDTO;
import com.Evidence_Service.entity.RecordInfo;
import org.springframework.stereotype.Component;

@Component
public class RecordInfoMapper {

    public RecordInfoDTO toDTO(RecordInfo entity) {
        if (entity == null) return null;
        return RecordInfoDTO.builder()
                .recordInfoId(entity.getRecordInfoId())
                .evidenceId(entity.getEvidenceId())
                .typeName(entity.getTypeName())
                .source(entity.getSource())
                .dateCollected(entity.getCreatedAt())
                .summary(entity.getSummary())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .isDeleted(entity.isDeleted())
                .build();
    }

    public RecordInfo toEntity(RecordInfoDTO dto) {
        if (dto == null) return null;
        return RecordInfo.builder()
                .recordInfoId(dto.getRecordInfoId())
                .evidenceId(dto.getEvidenceId())
                .typeName(dto.getTypeName())
                .source(dto.getSource())
                .createdAt(dto.getDateCollected())
                .summary(dto.getSummary())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .isDeleted(dto.isDeleted())
                .build();
    }
}