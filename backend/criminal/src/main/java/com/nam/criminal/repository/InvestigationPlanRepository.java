// InvestigationPlanRepository.java
package com.example.backend.repository;

import com.example.backend.entity.InvestigationPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestigationPlanRepository extends JpaRepository<InvestigationPlan, Long> {
}
