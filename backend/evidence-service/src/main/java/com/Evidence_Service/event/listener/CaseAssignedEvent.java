package com.Evidence_Service.event.listener;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseAssignedEvent {
    private String evidenceId;
    private String caseId;
    private LocalDateTime assignedAt;
}
