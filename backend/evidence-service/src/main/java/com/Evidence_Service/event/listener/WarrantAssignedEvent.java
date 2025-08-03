package com.Evidence_Service.event.listener;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarrantAssignedEvent {
    private String evidenceId;
    private String warrantResultId;
    private LocalDateTime assignedAt;
}

