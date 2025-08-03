/*
 * @ (#) SuspectRepository.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.repository;

import com.backend.commonservice.enums.SuspectStatus;
import com.backend.suspectservice.model.Suspect;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * @description Repository for Suspect entity
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
@RepositoryRestResource
@Hidden
public interface SuspectRepository extends JpaRepository<Suspect, String> {

    List<Suspect> getAllByIsDeletedFalse();

    List<Suspect> findByStatusAndIsDeletedFalse(SuspectStatus status);
}
