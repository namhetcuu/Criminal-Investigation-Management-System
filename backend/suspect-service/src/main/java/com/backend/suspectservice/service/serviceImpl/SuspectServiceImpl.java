/*
 * @ (#) SuspectServiceImpl.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.service.serviceImpl;

import com.backend.commonservice.enums.ErrorMessage;
import com.backend.commonservice.enums.SuspectStatus;
import com.backend.commonservice.model.AppException;
import com.backend.suspectservice.dto.request.SuspectCreateRequest;
import com.backend.suspectservice.dto.response.SuspectResponse;
import com.backend.suspectservice.mapper.SuspectMapper;
import com.backend.suspectservice.model.Suspect;
import com.backend.suspectservice.repository.SuspectRepository;
import com.backend.suspectservice.service.CloudinaryService;
import com.backend.suspectservice.service.SuspectService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
@Service
@RequiredArgsConstructor // Only create Contructor for final fields
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SuspectServiceImpl implements SuspectService {
    SuspectRepository suspectRep;
    SuspectMapper suspectMapper;
    CloudinaryService cloudinaryService;

    /**
     * Get all suspects from the repository.
     */
    @Override
    public List<SuspectResponse> getAllSuspects() {
        log.info("Getting all suspects");
        return suspectRep.getAllByIsDeletedFalse().stream().map(
                suspectMapper::toSuspectResponse
        ).collect(Collectors.toList());
    }

    /**
     * Get suspects by their status.
     *
     * @param status The status of the suspects to retrieve.
     * @return A list of suspects with the specified status.
     * @throws AppException if the status is null or empty.
     */
    @Override
    public List<SuspectResponse> getSuspectsByStatus(String status) {
        log.info("Getting suspects by status: {}", status);
        try {
            SuspectStatus suspectStatus = SuspectStatus.fromDescription(status);
            return suspectRep.findByStatusAndIsDeletedFalse(suspectStatus).stream()
                    .map(suspectMapper::toSuspectResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            log.warn("Status is null or empty, returning thrown");
            throw new AppException(ErrorMessage.SUSPECT_STATUS_NOT_FOUND);
        }
    }

    /**
     * Create a new suspect with the provided details and image.
     *
     * @param suspect      The details of the suspect to create.
     * @param suspectImage The image of the suspect to upload.
     * @return The created SuspectResponse.
     */
    @Transactional
    @Override
    public SuspectResponse createSuspect(SuspectCreateRequest suspect, MultipartFile suspectImage) {
        Suspect s = suspectMapper.createSuspect(suspect);
        String imageUrl = cloudinaryService.uploadImage(suspectImage);
        s.setMugshotUrl(imageUrl);
        return suspectMapper.toSuspectResponse(suspectRep.save(s));
    }

    /**
     * Update an existing suspect with the provided details.
     *
     * @param suspectId The ID of the suspect to update.
     * @param suspect   The new details of the suspect.
     * @return The updated SuspectResponse.
     */
    @Override
    public SuspectResponse updateSuspect(String suspectId, SuspectCreateRequest suspect) {
        return suspectRep.findById(suspectId)
                .map(existingSuspect -> {
                    suspectMapper.updateSuspect(existingSuspect, suspect);
                    return suspectMapper.toSuspectResponse(suspectRep.save(existingSuspect));
                })
                .orElseThrow(() -> new RuntimeException("Suspect not found with id: " + suspectId));
    }

    @Override
    public void deleteSuspect(String suspectId) {

    }

    @Override
    public List<Suspect> getSuspectsByCaseId(String caseId) {
        return List.of();
    }

    @Override
    public List<Suspect> searchSuspectsByName(String name) {
        return List.of();
    }

    @Override
    public Optional<Suspect> getSuspectById(String suspectId) {
        return Optional.empty();
    }
}
