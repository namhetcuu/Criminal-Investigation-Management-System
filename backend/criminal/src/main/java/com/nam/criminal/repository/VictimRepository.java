// VictimRepository.java
package com.example.backend.repository;

import com.example.backend.entity.Victim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VictimRepository extends JpaRepository<Victim, Long> {
}
