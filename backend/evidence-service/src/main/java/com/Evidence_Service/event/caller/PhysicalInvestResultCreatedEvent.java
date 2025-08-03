package com.Evidence_Service.event.caller;

import com.Evidence_Service.dto.PhysicalInvestResultDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicalInvestResultCreatedEvent {
    private String resultId;
    private String evidenceId;
    private String imageUrl;
    private String summary;
    private LocalDateTime createdAt;
    private String createdBy;

    public static PhysicalInvestResultCreatedEvent from(PhysicalInvestResultDTO entity) {
        PhysicalInvestResultCreatedEvent event = new PhysicalInvestResultCreatedEvent();
        event.setResultId(entity.getResultId());
        event.setCreatedAt(entity.getCreatedAt());
        return event;
    }
}
