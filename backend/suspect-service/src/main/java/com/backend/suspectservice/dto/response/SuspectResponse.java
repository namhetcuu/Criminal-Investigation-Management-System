/*
 * @ (#) SuspectResponse.java  1.0 7/7/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/7/2025
 * @version:    1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON serialization
public record SuspectResponse(
        String suspectId,
        String caseId,
        String fullName,
        String national,
        String address,
        String gender,
        String dob,
        String identification,
        String description,
        LocalDateTime catchTime,
        String notes,
        String mugshotUrl,
        String fingerprintsHash,
        String healthStatus,
        String status,
        boolean isDeleted
) { }
