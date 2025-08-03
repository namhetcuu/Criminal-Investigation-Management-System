package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseInvestResult;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "physical_invest_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PhysicalInvestResult extends BaseInvestResult {
    private PhysicalInvestStatus status;
    private String notes;
    private String imageUrl;
}