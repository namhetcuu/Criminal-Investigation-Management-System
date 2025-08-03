package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseInvestResult;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "forensic_invest_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ForensicInvestResult extends BaseInvestResult {
    private String labName;
    private String report;
    private String resultSummary;
}