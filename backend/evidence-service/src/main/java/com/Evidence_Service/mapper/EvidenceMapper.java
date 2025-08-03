package com.Evidence_Service.mapper;

import com.Evidence_Service.dto.EvidenceDTO;
import com.Evidence_Service.event.caller.EvidenceCreatedEvent;
import com.Evidence_Service.entity.Evidence;
import com.Evidence_Service.exception.AppException;
import com.Evidence_Service.exception.ErrorCode;

public class EvidenceMapper {
    public static EvidenceDTO toDTO(Evidence evidence) {
        if (evidence == null) return null;

        return EvidenceDTO.builder()
                .evidenceId(evidence.getEvidenceId())
                .description(evidence.getDescription())
                .detailedDescription(evidence.getDetailedDescription())
                .attachedFile(evidence.getAttachedFile())
                .initialCondition(evidence.getInitialCondition())
                .preservationMeasures(evidence.getPreservationMeasures())
                .locationAtScene(evidence.getLocationAtScene())
                .createdAt(evidence.getCreatedAt())
                .createdAt(evidence.getCreatedAt())
                .measureSurveyId(evidence.getMeasureSurveyId())
                .investigationPlanId(evidence.getInvestigationPlanId())
                .reportId(evidence.getReportId())
                .collectorUsername(evidence.getCollectorUsername())
                .isDeleted(evidence.isDeleted())
                .build();
    }

    public static Evidence toEntity(EvidenceDTO dto) {
        if (dto == null) return null;


        return Evidence.builder()
                .evidenceId(dto.getEvidenceId())
                .description(dto.getDescription())
                .detailedDescription(dto.getDetailedDescription())
                .attachedFile(dto.getAttachedFile())
                .initialCondition(dto.getInitialCondition())
                .preservationMeasures(dto.getPreservationMeasures())
                .locationAtScene(dto.getLocationAtScene())
                .createdAt(dto.getCreatedAt())
                .createdAt(dto.getCreatedAt())
                .measureSurveyId(dto.getMeasureSurveyId())
                .investigationPlanId(dto.getInvestigationPlanId())
                .reportId(dto.getReportId())
                .collectorUsername(dto.getCollectorUsername())
                .isDeleted(dto.isDeleted())
                .build();
    }

    public static EvidenceCreatedEvent toCreatedEvent(Evidence evidence) {
        if (evidence == null) return null;

        EvidenceCreatedEvent event = new EvidenceCreatedEvent();
        event.setEvidenceId(evidence.getEvidenceId());
        event.setCaseId(event.getCaseId());
        event.setLocation(event.getLocation());
        event.setReportId(event.getReportId());
        event.setCollectedAt(evidence.getCreatedAt());
        event.setCollector_username(event.getCollector_username());
        event.setSuspectId(event.getSuspectId());
        return event;
    }
}
