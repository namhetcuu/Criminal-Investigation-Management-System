// SuspectService.java
package com.example.backend.service;

import com.example.backend.entity.Suspect;
import java.util.List;

public interface SuspectService {
    Suspect addSuspect(Suspect suspect);
    List<Suspect> getAllSuspects();
}
