package com.Evidence_Service.event.listener;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuspectAssignedEvent {
    private String evidenceId;
    private String suspectId;
    private LocalDateTime assignedAt;
}

