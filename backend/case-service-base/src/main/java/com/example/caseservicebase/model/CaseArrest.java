package com.example.caseservicebase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseArrest {
    @Id
    @Column(name = "arrest_id")
    private Long arrestId;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case caseId;

    @Column(name = "suspect_id")
    private Long suspectId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
