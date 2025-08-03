package com.Evidence_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WarrantDTO {
    @NotBlank(message = "Warrant ID cannot be empty")
    @Size(max = 50, message = "Warrant ID must not exceed 50 characters")
    private String warrantId;
}