/*
 * @ (#) InmateService.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.service;

import com.backend.suspectservice.model.Inmate;

import java.util.List;
import java.util.Optional;

/*
 * @description Service interface for Inmate operations
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
public interface InmateService {

    List<Inmate> getAllInmates();

    Optional<Inmate> getInmateById(String inmateId);

    List<Inmate> getInmatesBySentenceId(String sentenceId);

    List<Inmate> getInmatesByFacility(String facility);

    List<Inmate> getInmatesByStatus(String status);

    List<Inmate> getInmatesByHealthStatus(String healthStatus);

    Inmate createInmate(Inmate inmate);

    Inmate updateInmate(String inmateId, Inmate inmate);

    void deleteInmate(String inmateId);

    boolean existsById(String inmateId);
}
