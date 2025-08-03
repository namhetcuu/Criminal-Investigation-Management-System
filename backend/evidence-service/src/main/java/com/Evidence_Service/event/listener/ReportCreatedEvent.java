package com.Evidence_Service.event.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportCreatedEvent {
    private String reportId;
    private List<String> evidenceIds;
}
