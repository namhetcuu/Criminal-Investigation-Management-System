/*
 * @ (#) SuspectRequest.java  1.0 7/7/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/7/2025
 * @version:    1.0
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        name = "SuspectCreateRequest",
        description = "Request model for creating a new suspect")
public class SuspectCreateRequest {
    @NotEmpty(message = "Case ID cannot be empty")
    @Schema(description = "Case id of suspect", example = "1")
    String caseId;
    @Size(min = 1, max = 50, message = "Full name must be between 1 and 50 characters")
    @Schema(description = "FullName Suspect", example = "John Doe")
    @NotEmpty(message = "Full name cannot be empty")
    String fullName;
    @Schema(description = "Address of suspect", example = "123 Main St, Springfield")
    @NotEmpty(message = "Address cannot be empty")
    String address;
//    @NotNull(message = "Status cannot be null")
//    SuspectStatus status;
    @Size(max = 2500)
    @Schema(description = "Description of the suspect", example = "Suspect is involved in a robbery case")
    String description;
}
