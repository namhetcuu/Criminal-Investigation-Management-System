package com.backend.investigationservice.event.listener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalInvestResultCreateEvent {
    private String result;
}
