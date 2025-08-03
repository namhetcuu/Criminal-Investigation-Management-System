package com.Evidence_Service.entity.id;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarrantEvidenceId implements Serializable {
    private String warrantId;
    private String evidenceId;
}
