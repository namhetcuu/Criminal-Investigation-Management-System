// WitnessRepository.java
package com.example.backend.repository;

import com.example.backend.entity.Witness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WitnessRepository extends JpaRepository<Witness, Long> {
}
