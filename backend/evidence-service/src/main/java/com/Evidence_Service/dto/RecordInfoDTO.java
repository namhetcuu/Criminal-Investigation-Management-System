package com.Evidence_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordInfoDTO {
    @NotBlank(message = "Record info ID cannot be empty")
    @Size(max = 50, message = "Record info ID must not exceed 50 characters")
    private String recordInfoId;

    @NotBlank(message = "Evidence ID cannot be empty")
    @Size(max = 50, message = "Evidence ID must not exceed 50 characters")
    private String evidenceId;

    @NotBlank(message = "Type name cannot be empty")
    @Size(max = 50, message = "Type name must not exceed 50 characters")
    private String typeName;

    @NotBlank(message = "Source cannot be empty")
    @Size(max = 100, message = "Source must not exceed 100 characters")
    private String source;

    @NotNull(message = "Date collected cannot be null")
    private LocalDateTime dateCollected;

    @NotBlank(message = "Summary cannot be empty")
    @Size(max = 500, message = "Summary must not exceed 500 characters")
    private String summary;

    @NotNull(message = "Created at timestamp cannot be null")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated at timestamp cannot be null")
    private LocalDateTime updatedAt;

    private boolean isDeleted;
}