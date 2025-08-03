package com.backend.investigationservice.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WitnessInterviewResponse {
    UUID witnessId;
    UUID interviewId;
}