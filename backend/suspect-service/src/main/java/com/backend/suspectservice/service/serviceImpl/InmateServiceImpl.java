/*
 * @ (#) InmateServiceImpl.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.service.serviceImpl;

import com.backend.suspectservice.model.Indictment;
import com.backend.suspectservice.service.IndictmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
@Service
public class InmateServiceImpl implements IndictmentService {
    @Override
    public List<Indictment> getAllIndictments() {
        return List.of();
    }

    @Override
    public Optional<Indictment> getIndictmentById(String indictmentId) {
        return Optional.empty();
    }

    @Override
    public List<Indictment> getIndictmentsByProsecutionId(String prosecutionId) {
        return List.of();
    }

    @Override
    public Indictment createIndictment(Indictment indictment) {
        return null;
    }

    @Override
    public Indictment updateIndictment(String indictmentId, Indictment indictment) {
        return null;
    }

    @Override
    public void deleteIndictment(String indictmentId) {

    }

    @Override
    public boolean existsById(String indictmentId) {
        return false;
    }
}
