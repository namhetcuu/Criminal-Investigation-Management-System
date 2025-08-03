/*
 * @ (#) UserService.java  1.0 7/8/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.service;


import com.backend.authservice.dto.request.UserCreationRequest;
import com.backend.authservice.dto.request.UserUpdateRequest;
import com.backend.authservice.dto.response.UserResponse;

import java.util.List;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/8/2025
 * @version:    1.0
 */
public interface UserService {
    UserResponse createUser(UserCreationRequest userCreationRequest);
    UserResponse getUserByUsername(String username);
    List<UserResponse> getAllUsers();
    UserResponse getMyInfo();
    UserResponse updateUser(String username, UserUpdateRequest userCreationRequest);
    void deleteUser(String username);
}
