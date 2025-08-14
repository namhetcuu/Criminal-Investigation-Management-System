// SuspectRepository.java
package com.example.backend.repository;

import com.example.backend.entity.Suspect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuspectRepository extends JpaRepository<Suspect, Long> {
}
