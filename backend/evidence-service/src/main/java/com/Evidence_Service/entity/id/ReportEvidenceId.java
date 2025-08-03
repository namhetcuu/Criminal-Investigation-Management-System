package com.Evidence_Service.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportEvidenceId implements Serializable {
    private String reportId;
    private String evidenceId;
}
