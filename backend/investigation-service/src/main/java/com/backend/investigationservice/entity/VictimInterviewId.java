package com.backend.investigationservice.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VictimInterviewId implements java.io.Serializable {
    @Column(name = "victim_id")
    private UUID victimId;

    @Column(name = "interview_id")
    private UUID interviewId;
}
