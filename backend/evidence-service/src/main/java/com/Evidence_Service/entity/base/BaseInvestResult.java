package com.Evidence_Service.entity.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseInvestResult extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "result_id")
    protected String resultId;

    @Column(name = "evidence_id")
    protected String evidenceId;

    protected String uploadFile;

    protected String result;
}