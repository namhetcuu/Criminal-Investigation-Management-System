package com.backend.authservice.dto.response;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}