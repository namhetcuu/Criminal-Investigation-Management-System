/*
 * @ (#) PermissionServiceImpl.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.service.serviceImpl;

import com.backend.authservice.dto.request.PermissionCreationRequest;
import com.backend.authservice.dto.response.PermissionResponse;
import com.backend.authservice.entity.Permission;
import com.backend.authservice.mapper.PermissionMapper;
import com.backend.authservice.repository.PermissionRepository;
import com.backend.authservice.service.PermissionService;
import com.backend.commonservice.enums.ErrorMessage;
import com.backend.commonservice.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRep;
    PermissionMapper permissionMapper;

    /**
     * Retrieves all permissions that are not deleted.
     *
     * @return a list of PermissionResponse objects representing all non-deleted permissions
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Override
    public List<PermissionResponse> getAllPermissions() {
      return  permissionRep.findAll()
                .stream()
                .map(permissionMapper::toPermissionRes)
                .toList();
    }

    /**
     * Retrieves a permission by its description.
     *
     * @param description the description of the permission to retrieve
     * @return the PermissionResponse object representing the permission
     * @throws AppException if the permission is not found
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Override
    public PermissionResponse getByDescription(String description) {
        Permission permission = permissionRep.findByDescription(description)
                .orElseThrow(() -> new AppException(ErrorMessage.PERMISSION_NOT_FOUND));
        return permissionMapper.toPermissionRes(permission);
    }

    /**
     * Creates a new permission.
     *
     * @param per the permission creation request
     * @return the created permission response
     * @throws AppException if the permission already exists
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Transactional
    @Override
    public PermissionResponse createPermission(PermissionCreationRequest per) {
    Permission permission = permissionMapper.toPermission(per);
    // Check if the permission already exists
    if (permissionRep.existsByDescription(permission.getDescription())) {
        throw new AppException(ErrorMessage.PERMISSION_ALREADY_EXISTS);
    }
        // Save the new permission
    return permissionMapper.toPermissionRes(permissionRep.save(permission));
    }

    /**
     * Deletes a permission by its ID.
     *
     * @param permissionId the ID of the permission to delete
     * @throws AppException if the permission is not found
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Transactional
    @Override
    public void deletePermission(String permissionId) {
        permissionRep.deletePermissionByPermissionId(permissionId)
                .orElseThrow(() -> new AppException(ErrorMessage.PERMISSION_NOT_FOUND));
    }

    /**
     * Updates an existing permission.
     *
     * @param permissionId the ID of the permission to update
     * @param per          the permission creation request containing updated details
     * @return the updated permission response
     * @throws AppException if the permission is not found
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Transactional
    @Override
    public PermissionResponse updatePermission(String permissionId, PermissionCreationRequest per) {
        log.info("Updating permission with ID: {}", permissionId);
        // Check if the permission exists
        Permission permission = permissionRep.findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorMessage.PERMISSION_NOT_FOUND));
        // Update the permission details
        permission.setDescription(per.getDescription());
        // Save the updated permission
        return permissionMapper.toPermissionRes(permissionRep.save(permission));
    }
}
