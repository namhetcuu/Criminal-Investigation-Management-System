/*
 * @ (#) AuthService.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.service;


import com.backend.authservice.dto.request.IntrospectRequest;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
public interface AuthService {
    String authenticate(String username, String password);

    void logout(IntrospectRequest token);
}
