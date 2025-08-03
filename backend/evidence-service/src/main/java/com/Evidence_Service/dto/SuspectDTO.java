package com.Evidence_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SuspectDTO {
    @NotBlank(message = "Suspect ID cannot be empty")
    @Size(max = 50, message = "Suspect ID must not exceed 50 characters")
    private String suspectId;
}