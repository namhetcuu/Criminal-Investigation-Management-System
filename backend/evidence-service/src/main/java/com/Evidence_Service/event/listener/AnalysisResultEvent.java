package com.Evidence_Service.event.listener;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisResultEvent {
    private String evidenceId;
    private String typeName; // DIGITAL, FORENSIC, PHYSICAL
    private String result;
    private String measureSurveyId;
    private LocalDateTime createdAt;
}

