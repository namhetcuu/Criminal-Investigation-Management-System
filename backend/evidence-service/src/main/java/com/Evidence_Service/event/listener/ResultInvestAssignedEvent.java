package com.Evidence_Service.event.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInvestAssignedEvent {
    private String investigationPlanId;
    private String evidenceId;
    private String uploadFile;
    private String content;
    private String type; //forensic, physical, financial, digital
}
