/*
 * @ (#) IndictmentService.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.service;

import com.backend.suspectservice.model.Indictment;

import java.util.List;
import java.util.Optional;

/*
 * @description Service interface for Indictment operations
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
public interface IndictmentService {

    List<Indictment> getAllIndictments();

    Optional<Indictment> getIndictmentById(String indictmentId);

    List<Indictment> getIndictmentsByProsecutionId(String prosecutionId);

    Indictment createIndictment(Indictment indictment);

    Indictment updateIndictment(String indictmentId, Indictment indictment);

    void deleteIndictment(String indictmentId);

    boolean existsById(String indictmentId);
}
