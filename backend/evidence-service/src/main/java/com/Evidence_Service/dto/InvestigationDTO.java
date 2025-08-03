package com.Evidence_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationDTO {
    @NotBlank(message = "Result ID cannot be empty")
    @Size(max = 50, message = "Result ID must not exceed 50 characters")
    private String resultId;

    @NotBlank(message = "Type cannot be empty")
    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;

    @NotBlank(message = "Upload file cannot be empty")
    @Size(max = 255, message = "Upload file path must not exceed 255 characters")
    private String uploadFile;

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 1000, message = "Content must not exceed 1000 characters")
    private String content;

    @NotNull(message = "Created at timestamp cannot be null")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated at timestamp cannot be null")
    private LocalDateTime updatedAt;
}