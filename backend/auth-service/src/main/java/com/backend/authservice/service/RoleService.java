/*
 * @ (#) RoleService.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.service;


import com.backend.authservice.dto.request.RoleCreationRequest;
import com.backend.authservice.dto.response.RoleResponse;

import java.util.List;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
public interface RoleService {
    RoleResponse getRoleByRoleId(String roleId);

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleByDescription(String description);

    RoleResponse createRole(RoleCreationRequest roleCreationRequest);

    void deleteRole(String roleId);
    RoleResponse updateRole(String roleId, RoleCreationRequest roleCreationRequest);
}
