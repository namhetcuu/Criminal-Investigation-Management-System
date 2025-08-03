/*
 * @ (#) RoleMapper.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.mapper;


import com.backend.authservice.dto.request.RoleCreationRequest;
import com.backend.authservice.dto.response.RoleResponse;
import com.backend.authservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleCreationRequest role);

    RoleResponse toRoleResponse(Role role);
}
