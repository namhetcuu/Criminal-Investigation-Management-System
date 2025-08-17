package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cases")
public class CaseEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caseNumber;

    @Column(columnDefinition = "TEXT")
    private String summary;

    // Liên kết với Report
    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

    // Các mối quan hệ
    @OneToMany(mappedBy = "caseEntity", cascade = CascadeType.ALL)
    private List<Evidence> evidenceList;

    @OneToMany(mappedBy = "caseEntity", cascade = CascadeType.ALL)
    private List<Victim> victims;

    @OneToMany(mappedBy = "caseEntity", cascade = CascadeType.ALL)
    private List<Suspect> suspects;

    @OneToMany(mappedBy = "caseEntity", cascade = CascadeType.ALL)
    private List<Witness> witnesses;

    @OneToMany(mappedBy = "caseEntity", cascade = CascadeType.ALL)
    private List<InvestigationPlan> investigationPlans;
}
