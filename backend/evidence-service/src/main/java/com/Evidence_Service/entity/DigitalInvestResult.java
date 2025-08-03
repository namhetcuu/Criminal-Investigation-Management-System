package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseInvestResult;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "digital_invest_result")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DigitalInvestResult extends BaseInvestResult {
    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "analyst_tool")
    private String analystTool;

    @Column(name = "result")
    private String result;
}
