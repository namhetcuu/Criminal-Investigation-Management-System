/*
 * @ (#) ProsecutionSuspectRepository.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.repository;

import com.backend.suspectservice.model.ProsecutionSuspect;
import com.backend.suspectservice.model.ProsecutionSuspectId;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * @description Repository for ProsecutionSuspect entity
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
@RepositoryRestResource
@Hidden
public interface ProsecutionSuspectRepository extends JpaRepository<ProsecutionSuspect, ProsecutionSuspectId> {

    @Query("SELECT ps FROM ProsecutionSuspect ps WHERE ps.isDeleted = false")
    List<ProsecutionSuspect> findAllActive();

    @Query("SELECT ps FROM ProsecutionSuspect ps WHERE ps.prosecutionSuspectId = :id AND ps.isDeleted = false")
    Optional<ProsecutionSuspect> findByIdActive(@Param("id") ProsecutionSuspectId id);

    @Query("SELECT ps FROM ProsecutionSuspect ps WHERE ps.prosecutionSuspectId.prosecutionId = :prosecutionId AND ps.isDeleted = false")
    List<ProsecutionSuspect> findByProsecutionIdActive(@Param("prosecutionId") String prosecutionId);

    @Query("SELECT ps FROM ProsecutionSuspect ps WHERE ps.prosecutionSuspectId.suspectId = :suspectId AND ps.isDeleted = false")
    List<ProsecutionSuspect> findBySuspectIdActive(@Param("suspectId") String suspectId);
}
