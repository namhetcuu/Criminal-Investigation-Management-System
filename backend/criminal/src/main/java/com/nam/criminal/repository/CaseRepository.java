// CaseRepository.java
package com.example.backend.repository;

import com.example.backend.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
}
