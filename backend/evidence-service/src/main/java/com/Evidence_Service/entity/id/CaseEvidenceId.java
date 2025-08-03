package com.Evidence_Service.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseEvidenceId implements Serializable {
    private String caseId;
    private String evidenceId;
}
