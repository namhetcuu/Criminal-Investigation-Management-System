/*
 * @ (#) PermissionService.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.service;


import com.backend.authservice.dto.request.PermissionCreationRequest;
import com.backend.authservice.dto.response.PermissionResponse;

import java.util.List;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
public interface PermissionService {
    List<PermissionResponse> getAllPermissions();

    PermissionResponse getByDescription(String description);

    PermissionResponse createPermission(PermissionCreationRequest per);

    void deletePermission(String permissionId);

    PermissionResponse updatePermission(String permissionId, PermissionCreationRequest per);


}
