package com.backend.investigationservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WitnessInterviewRequest {
    @JsonProperty("witness_id")
    @NotBlank
    UUID witnessId;

    @JsonProperty("interview_id")
    @NotNull
    UUID interviewId;
}
