/*
 * @ (#) CaseEvidenceRepository.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */
package com.example.caseservicebase.repository;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/10/2025
 * @version 1.0
 */
import com.example.caseservicebase.model.Case;
import com.example.caseservicebase.model.CaseEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseEvidenceRepository extends JpaRepository<CaseEvidence, Long> {
    Optional<CaseEvidence> findByEvidenceIdAndIsDeletedFalse(Long evidenceId);
    List<CaseEvidence> findAllByIsDeletedFalse();
}