/*
 * @ (#) Permission.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.controller;

import com.backend.authservice.dto.request.PermissionCreationRequest;
import com.backend.authservice.dto.response.PermissionResponse;
import com.backend.authservice.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
@Tag(name = "Permission Query", description = "Permission API")
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {
    PermissionService permissionService;


    /**
     * Retrieves all permissions.
     *
     * @return a list of all permissions
     */
    @Operation(
            summary = "Get All Permissions",
            description = "Retrieves a list of all permissions."
    )
    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {
        List<PermissionResponse> permissions = permissionService.getAllPermissions();
        log.info("Retrieved {} permissions", permissions.size());
        return ResponseEntity.ok(permissions);
    }

    /**
     * Creates a new permission.
     *
     * @param request the permission creation request
     * @return the created permission response
     */
    @Operation(
            summary = "Create Permission",
            description = "Creates a new permission with the provided details."
    )
    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(
            @RequestBody @Valid PermissionCreationRequest request
    ) {
        PermissionResponse response = permissionService.createPermission(request);
        log.info("Permission created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Deletes a permission by its ID.
     *
     * @param permissionId the ID of the permission to delete
     * @return a response indicating the deletion status
     */
    @Operation(
            summary = "Delete Permission",
            description = "Deletes a permission by its ID."
    )
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Map<String, String>> deletePermission(@PathVariable String permissionId) {
        permissionService.deletePermission(permissionId);
        log.info("Permission with ID {} deleted successfully", permissionId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Permission deleted successfully");
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves a permission by its description.
     *
     * @param description the description of the permission to retrieve
     * @return the permission response
     */
    @Operation(
            summary = "Get Permission by Description",
            description = "Retrieves a permission by its description."
    )
    @GetMapping("/description/{description}")
    public ResponseEntity<PermissionResponse> getByDescription(@PathVariable String description) {
        PermissionResponse response = permissionService.getByDescription(description);
        log.info("Retrieved permission by description: {}", response);
        return ResponseEntity.ok(response);
    }
    /**
     * Updates a permission by its ID.
     *
     * @param permissionId the ID of the permission to update
     * @param request      the permission creation request with updated details
     * @return the updated permission response
     */
    @Operation(
            summary = "Update Permission",
            description = "Updates a permission by its ID."
    )
    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionResponse> updatePermission(
            @PathVariable String permissionId,
            @RequestBody @Valid PermissionCreationRequest request
    ) {
        PermissionResponse response = permissionService.updatePermission(permissionId, request);
        log.info("Permission with ID {} updated successfully: {}", permissionId, response);
        return ResponseEntity.ok(response);
    }
}
