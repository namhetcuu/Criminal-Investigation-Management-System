/*
 * @ (#) CaseArrestRepository.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */
package com.example.caseservicebase.repository;

import com.example.caseservicebase.model.Case;
import com.example.caseservicebase.model.CaseArrest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/10/2025
 * @version 1.0
 */
@Repository
public interface CaseArrestRepository extends JpaRepository<CaseArrest, Long> {
    Optional<CaseArrest> findByArrestIdAndIsDeletedFalse(Long arrestId);
    List<CaseArrest> findAllByIsDeletedFalse();
}
