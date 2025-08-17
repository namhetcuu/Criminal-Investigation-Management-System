// WitnessService.java
package com.example.backend.service;

import com.example.backend.entity.Witness;
import java.util.List;

public interface WitnessService {
    Witness addWitness(Witness witness);
    List<Witness> getAllWitnesses();
}
