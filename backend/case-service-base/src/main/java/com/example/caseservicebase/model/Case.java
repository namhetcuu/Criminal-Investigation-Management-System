package com.example.caseservicebase.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Stack;

@Entity
@Getter
@Setter
@Table(name = "cases")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    private Long caseId;

    @Column(name = "case_number")
    private String caseNumber;

    @Column(name = "type_case")
    private String typeCase;

    @Column(name = "severity")
    private String severity;

    @Column(name = "status")
    private String status;

    @Column(name = "summary")
    private String summary;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "case_target")
    private String caseTarget;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
