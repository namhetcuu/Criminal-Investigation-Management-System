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
public class VictimInterviewRequest {
    @JsonProperty("victim_id")
    @NotBlank
    String victimId;

    @JsonProperty("interview_id")
    @NotNull
    UUID interviewId;
}
