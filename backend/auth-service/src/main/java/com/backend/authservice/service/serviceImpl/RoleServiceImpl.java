/*
 * @ (#) RoleImpl.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.service.serviceImpl;

import com.backend.authservice.dto.request.RoleCreationRequest;
import com.backend.authservice.dto.response.RoleResponse;
import com.backend.authservice.entity.Role;
import com.backend.authservice.mapper.RoleMapper;
import com.backend.authservice.repository.PermissionRepository;
import com.backend.authservice.repository.RoleRepository;
import com.backend.authservice.service.RoleService;
import com.backend.commonservice.enums.ErrorMessage;
import com.backend.commonservice.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRep;
    RoleMapper roleMapper;
    PermissionRepository permissionRep;

    /**
     * Get role by role ID.
     *
     * @param roleId The ID of the role to retrieve.
     * @return The role response containing role details.
     */
    @Override
    public RoleResponse getRoleByRoleId(String roleId) {
        log.info("Fetching role with ID: {}", roleId);
        Role role = roleRep.findRoleByRoleId(roleId)
                .orElseThrow(() -> new AppException(ErrorMessage.ROLE_NOT_FOUND));
        return roleMapper.toRoleResponse(role);
    }

    /**
     * Get all roles in the system.
     *
     * @return A list of role responses containing details of all roles.
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRep.getAllRoles().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    /**
     * Get role by description.
     *
     * @param description The description of the role to retrieve.
     * @return The role response containing role details.
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Override
    public RoleResponse getRoleByDescription(String description) {
        log.info("Fetching role with description: {}", description);
        Role role = roleRep.findRoleByDescription(description)
                .orElseThrow(() -> new AppException(ErrorMessage.ROLE_NOT_FOUND));
        return roleMapper.toRoleResponse(role);
    }

    /**
     * Create a new role with the specified permissions.
     *
     * @param roleCreationRequest The request containing role details and permissions.
     * @return The created role response.
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Transactional
    @Override
    public RoleResponse createRole(RoleCreationRequest roleCreationRequest) {
        log.info("Creating role with description: {}", roleCreationRequest.getDescription());
        Role role = roleMapper.toRole(roleCreationRequest);

        // Check if the role already exists by description
        roleRep.findRoleByDescription(roleCreationRequest.getDescription())
                 .orElseThrow(() -> new AppException(ErrorMessage.ROLE_ALREADY_EXISTS));

        // Check if permissions exist
        var permissions = permissionRep.findAllById(roleCreationRequest.getPermissions());
        if (permissions.isEmpty()) {
            log.error("Permissions not found for IDs: {}", roleCreationRequest.getPermissions());
            throw new AppException(ErrorMessage.PERMISSION_NOT_FOUND);
        }
        // Set permissions to the role
        role.setPermissions(new HashSet<>(permissions));
        log.info("Saving new role with description: {}", role.getDescription());
        return roleMapper.toRoleResponse(roleRep.save(role));
    }

    /**
     * Delete a role by its ID.
     *
     * @param roleId The ID of the role to delete.
     * @throws AppException if the role is not found.
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Transactional
    @Override
    public void deleteRole(String roleId) {
        log.info("Deleting role with ID: {}", roleId);
        // Find the role by ID
        Role role = roleRep.findRoleByRoleId(roleId)
                .orElseThrow(() -> new AppException(ErrorMessage.ROLE_NOT_FOUND));
        role.setDeleted(true); // Mark the role as deleted
        roleRep.save(role);
        log.info("Role with ID {} deleted successfully", roleId);
    }

    /**
     * Update an existing role.
     *
     * @param roleId               The ID of the role to update.
     * @param roleCreationRequest  The request containing updated role details and permissions.
     * @return The updated role response.
     * @throws AppException if the role is not found or if the description already exists.
     */
    @PreAuthorize("hasAuthority('ALL')")
    @Transactional
    @Override
    public RoleResponse updateRole(String roleId, RoleCreationRequest roleCreationRequest) {
        log.info("Updating role with ID: {}", roleId);
        // Find the existing role by ID
        Role existingRole = roleRep.findRoleByRoleId(roleId)
                .orElseThrow(() -> new AppException(ErrorMessage.ROLE_NOT_FOUND));

        // Check if the description already exists
        roleRep.findRoleByDescription(roleCreationRequest.getDescription())
                .orElseThrow(() -> new AppException(ErrorMessage.ROLE_ALREADY_EXISTS));

        // Check if permissions exist
        var permissions = permissionRep.findAllById(roleCreationRequest.getPermissions());
        if (permissions.isEmpty()) {
            log.error("Permissions not found for IDs: {}", roleCreationRequest.getPermissions());
            throw new AppException(ErrorMessage.PERMISSION_NOT_FOUND);
        }
        // Update role details
        existingRole.setDescription(roleCreationRequest.getDescription());
        existingRole.setPermissions(new HashSet<>(permissions));

        log.info("Saving updated role with ID: {}", roleId);
        return roleMapper.toRoleResponse(roleRep.save(existingRole));
    }
}
