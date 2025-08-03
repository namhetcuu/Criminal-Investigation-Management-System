/*
 * @ (#) PermissionMapper.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.mapper;


import com.backend.authservice.dto.request.PermissionCreationRequest;
import com.backend.authservice.dto.response.PermissionResponse;
import com.backend.authservice.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionRes(Permission permission);
    Permission toPermission(PermissionCreationRequest per);
}
