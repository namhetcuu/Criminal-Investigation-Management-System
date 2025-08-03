package com.backend.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoleResponse(
        String roleId,
        String description,
        Set<PermissionResponse> permissions
) { }
