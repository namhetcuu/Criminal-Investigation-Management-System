/*
 * @ (#) SuspectStatus.java  1.0 7/7/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.commonservice.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum SuspectStatus {
    INTERVIEWED("Interviewed"),
    NOT_YET_CATCH("Not Yet Catch"),
    WAITING_FOR_INTERVIEW("Waiting for interview");
    private final String description;

    SuspectStatus(String description) {
        this.description = description;
    }

    /**
     * Find SuspectStatus by its description.
     *
     * @param description description of the SuspectStatus
     * @return SuspectStatus corresponding to the description
     * @throws IllegalArgumentException if no SuspectStatus with the given description exists
     */
    public static SuspectStatus fromDescription(String description) {
        for (SuspectStatus status : values()) {
            if (status.description.equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Not find Suspect status: " + description);
    }
}
