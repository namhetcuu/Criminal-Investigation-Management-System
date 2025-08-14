// VictimService.java
package com.example.backend.service;

import com.example.backend.entity.Victim;
import java.util.List;

public interface VictimService {
    Victim addVictim(Victim victim);
    List<Victim> getAllVictims();
}
