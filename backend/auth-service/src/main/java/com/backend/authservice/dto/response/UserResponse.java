/*
 * @ (#) UserResponse.java  1.0 7/8/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/8/2025
 * @version:    1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        String userName,
        String fullName,
        String avatarUrl,
        String email,
        String phoneNumber,
        LocalDateTime createAt,
        RoleResponse roleResponse
) {
}
