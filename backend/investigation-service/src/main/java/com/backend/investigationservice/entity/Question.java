package com.backend.investigationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "question_id", nullable = false)
    private UUID questionId;

    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "answer", length = 1000)
    private String answer;

    @Column(name = "reliability", length = 100)
    private String reliability;

    @Column(name = "interview_id")
    private UUID interviewId;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
