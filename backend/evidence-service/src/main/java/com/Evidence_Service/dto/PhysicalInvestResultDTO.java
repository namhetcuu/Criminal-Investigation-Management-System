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
public class PhysicalInvestResultDTO {
    @NotBlank(message = "Result ID cannot be empty")
    @Size(max = 50, message = "Result ID must not exceed 50 characters")
    private String resultId;

    @NotBlank(message = "Evidence ID cannot be empty")
    @Size(max = 50, message = "Evidence ID must not exceed 50 characters")
    private String evidenceId;

    @NotBlank(message = "Status cannot be empty")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    private String status;

    @NotBlank(message = "Notes cannot be empty")
    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    @NotBlank(message = "Image URL cannot be empty")
    @Size(max = 255, message = "Image URL must not exceed 255 characters")
    private String imageUrl;

    @NotNull(message = "Created at timestamp cannot be null")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated at timestamp cannot be null")
    private LocalDateTime updatedAt;

    private boolean isDeleted;
}