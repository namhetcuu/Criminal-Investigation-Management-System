package com.Evidence_Service.event.caller;

import com.Evidence_Service.dto.FinancialInvestResultDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialInvestResultCreatedEvent {
    private String resultId;
    private String evidenceId;
    private String summary;
    private String attachedFile;
    private LocalDateTime createdAt;

    public static FinancialInvestResultCreatedEvent from(FinancialInvestResultDTO dto) {
        return new FinancialInvestResultCreatedEvent(
                dto.getResultId(),
                dto.getEvidenceId(),
                dto.getSummary(),
                dto.getAttachedFile(),
                dto.getCreatedAt()
        );
    }
}

