// EvidenceService.java
package com.example.backend.service;

import com.example.backend.entity.Evidence;
import java.util.List;

public interface EvidenceService {
    Evidence addEvidence(Evidence evidence);
    List<Evidence> getAllEvidence();
}
