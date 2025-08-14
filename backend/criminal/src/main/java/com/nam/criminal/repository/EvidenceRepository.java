// EvidenceRepository.java
package com.example.backend.repository;

import com.example.backend.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
}
