package com.Evidence_Service.event.caller;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvidenceDeletedEvent {
    private String evidenceId;
    private String caseId;
    private String reportId;
    private String suspectId;
    private LocalDateTime deletedAt;

    public EvidenceDeletedEvent(String evidenceId){
        this.evidenceId = evidenceId;
    }
}
