package com.Evidence_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasureSurveyDTO {
    @NotBlank(message = "Measure survey ID cannot be empty")
    @Size(max = 50, message = "Measure survey ID must not exceed 50 characters")
    private String measureSurveyId;

    @NotBlank(message = "Source cannot be empty")
    @Size(max = 100, message = "Source must not exceed 100 characters")
    private String source;

    @NotBlank(message = "Type name cannot be empty")
    @Size(max = 50, message = "Type name must not exceed 50 characters")
    private String typeName;

    @NotBlank(message = "Result ID cannot be empty")
    @Size(max = 50, message = "Result ID must not exceed 50 characters")
    private String resultId;

    @NotNull(message = "Created at timestamp cannot be null")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated at timestamp cannot be null")
    private LocalDateTime updatedAt;

    private boolean isDeleted;
}