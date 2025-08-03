package com.Evidence_Service.event.caller;

import com.Evidence_Service.dto.DigitalInvestResultDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DigitalInvestResultCreatedEvent {
    private String resultId;
    private String evidenceId;
    private String investigationPlanId;
    private String deviceType;
    private String analystTool;
    private String result;
    private LocalDateTime createdAt;
    private String createdBy;

    public static DigitalInvestResultCreatedEvent from(DigitalInvestResultDTO dto) {
        DigitalInvestResultCreatedEvent event = new DigitalInvestResultCreatedEvent();
        event.setResultId(dto.getResultId());
        event.setEvidenceId(dto.getEvidenceId());
        event.setDeviceType(dto.getDeviceType());
        event.setAnalystTool(dto.getAnalystTool());
        event.setResult(dto.getResult());
        event.setCreatedAt(dto.getCreatedAt());
        return event;
    }
}
