/*
 * @ (#) CustomAccessDeniedHandler.java  1.0 7/10/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.commonservice.config;

import com.backend.commonservice.enums.ErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/10/2025
 * @version:    1.0
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(ErrorMessage.UNAUTHORIZED.getCode());
        response.setContentType("application/json");
        String message = ErrorMessage.UNAUTHORIZED.getMessage();
        int code = ErrorMessage.UNAUTHORIZED.getCode();
        // dùng UTF-8 để tránh lỗi mã hóa
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\", \"code\": " + code + "}");
    }
}
