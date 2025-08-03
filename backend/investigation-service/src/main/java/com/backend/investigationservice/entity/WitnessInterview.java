package com.backend.investigationservice.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "witness_interviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WitnessInterview {

    @EmbeddedId
    private WitnessInterviewId id;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @MapsId("interviewId")
    @JoinColumn(name = "interview_id", insertable = false, updatable = false)
    private Interview interview;
}

