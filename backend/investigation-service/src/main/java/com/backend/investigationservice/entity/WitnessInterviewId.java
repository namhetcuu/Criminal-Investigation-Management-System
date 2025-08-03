package com.backend.investigationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WitnessInterviewId implements java.io.Serializable {
    @Column(name = "witness_id")
    private UUID witnessId;

    @Column(name = "interview_id")
    private UUID interviewId;
}
