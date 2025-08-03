package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseInvestResult;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "financial_invest_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FinancialInvestResult extends BaseInvestResult {
    private String summary;
    private String attachedFile;
}
