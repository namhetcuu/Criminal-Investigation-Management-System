// InvestigationPlanService.java
package com.example.backend.service;

import com.example.backend.entity.InvestigationPlan;
import java.util.List;

public interface InvestigationPlanService {
    InvestigationPlan addPlan(InvestigationPlan plan);
    List<InvestigationPlan> getAllPlans();
}
