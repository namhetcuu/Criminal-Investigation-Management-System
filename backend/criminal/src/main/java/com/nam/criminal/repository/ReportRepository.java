// ReportRepository.java
package com.example.backend.repository;

import com.example.backend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
