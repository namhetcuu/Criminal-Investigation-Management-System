package com.Evidence_Service.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class FeignHeaderProvider {

    public String getAuthToken() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? request.getHeader("Authorization") : null;
    }

    public String getApiKey() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? request.getHeader("X-API-KEY") : null;
    }

    public String getUsername() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? request.getHeader("X-USERNAME") : null;
    }

    public String getPermissions() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? request.getHeader("X-PERMISSION") : null;
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
