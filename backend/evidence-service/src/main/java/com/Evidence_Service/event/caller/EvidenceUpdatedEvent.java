package com.Evidence_Service.event.caller;

import com.Evidence_Service.dto.EvidenceDTO;

public class EvidenceUpdatedEvent {
    private String evidenceId;
    private EvidenceDTO evidenceDTO;

    public EvidenceUpdatedEvent(String evidenceId, EvidenceDTO evidenceDTO) {
        this.evidenceId = evidenceId;
        this.evidenceDTO = evidenceDTO;
    }
}
