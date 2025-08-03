/*
 * @ (#) SuspectService.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.service;

import com.backend.suspectservice.dto.request.SuspectCreateRequest;
import com.backend.suspectservice.dto.response.SuspectResponse;
import com.backend.suspectservice.model.Suspect;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/*
 * @description Service interface for Suspect operations
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
public interface SuspectService {

    List<SuspectResponse> getAllSuspects();

    List<SuspectResponse> getSuspectsByStatus(String status);

    SuspectResponse createSuspect(SuspectCreateRequest suspect, MultipartFile suspectImage);

    Optional<Suspect> getSuspectById(String suspectId);

    List<Suspect> getSuspectsByCaseId(String caseId);

    List<Suspect> searchSuspectsByName(String name);

    SuspectResponse updateSuspect(String suspectId, SuspectCreateRequest suspect);

    void deleteSuspect(String suspectId);
}
