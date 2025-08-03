package com.Evidence_Service.event.caller;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvidenceCreatedEvent {
    private String evidenceId;
    private String collector_username;
    private LocalDateTime collectedAt;
    private String location;
    private String caseId;
    private String reportId;
    private String suspectId;
}
