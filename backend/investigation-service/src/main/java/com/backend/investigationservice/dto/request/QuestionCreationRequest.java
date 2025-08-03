package com.backend.investigationservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCreationRequest {
    @JsonProperty("content")
    @NotBlank(message = "Question content must not be blank")
    @Size(max = 1000, message = "Question content must not exceed 1000 characters")
    private String content;

    @JsonProperty("answer")
    @Size(max = 1000, message = "Answer must not exceed 1000 characters")
    private String answer;

    @JsonProperty("reliability")
    @Size(max = 100, message = "Reliability must not exceed 100 characters")
    private String reliability;
}
