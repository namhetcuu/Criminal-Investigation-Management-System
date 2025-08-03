package com.backend.investigationservice.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "victim_interviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VictimInterview {

    @EmbeddedId
    private VictimInterviewId id;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @MapsId("interviewId")
    @JoinColumn(name = "interview_id", insertable = false, updatable = false)
    private Interview interview;
}
