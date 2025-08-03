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
public class DigitalInvestResultDTO {
    @NotBlank(message = "Result ID cannot be empty")
    @Size(max = 50, message = "Result ID must not exceed 50 characters")
    private String resultId;

    @NotBlank(message = "Evidence ID cannot be empty")
    @Size(max = 50, message = "Evidence ID must not exceed 50 characters")
    private String evidenceId;

    @NotBlank(message = "Device type cannot be empty")
    @Size(max = 50, message = "Device type must not exceed 50 characters")
    private String deviceType;

    @NotBlank(message = "Analyst tool cannot be empty")
    @Size(max = 100, message = "Analyst tool must not exceed 100 characters")
    private String analystTool;

    @NotBlank(message = "Result cannot be empty")
    @Size(max = 500, message = "Result must not exceed 500 characters")
    private String result;

    @NotBlank(message = "Image URL cannot be empty")
    @Size(max = 255, message = "Image URL must not exceed 255 characters")
    private String imageUrl;

    @NotBlank(message = "Notes cannot be empty")
    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    @NotNull(message = "Created at timestamp cannot be null")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated at timestamp cannot be null")
    private LocalDateTime updatedAt;

    private boolean isDeleted;
}