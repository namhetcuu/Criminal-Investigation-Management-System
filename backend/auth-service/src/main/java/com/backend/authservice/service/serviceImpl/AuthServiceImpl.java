/*
 * @ (#) AuthServiceImpl.java  1.0 7/9/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.service.serviceImpl;

import com.backend.authservice.dto.request.IntrospectRequest;
import com.backend.authservice.entity.User;
import com.backend.authservice.repository.PermissionRepository;
import com.backend.authservice.repository.UserRepository;
import com.backend.authservice.service.AuthService;
import com.backend.commonservice.enums.ErrorMessage;
import com.backend.commonservice.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/9/2025
 * @version:    1.0
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class AuthServiceImpl implements AuthService {

    UserRepository userRep;
    PermissionRepository permissionRep;
    JwtEncoder jwtEncoder;
    JwtDecoder jwtDecoder;
    PasswordEncoder passwordEncoder;

    /**
     * Authenticate a user with the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A JWT token if authentication is successful.
     * @throws AppException if the user is not found or if the credentials are invalid.
     */
    @Override
    public String authenticate(String username, String password) {
        log.info("Authenticating user with username: {}", username);
        User user = userRep.findUserByUserName(username)
                .orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND));
        boolean result = passwordEncoder.matches(password, user.getPasswordHash());
        if (!result) {
            log.error("Authentication failed for user: {}", username);
            throw new AppException(ErrorMessage.INVALID_CREDENTIALS);
        }
        log.info("Authentication successful for user: {}", username);
        return generateToken(user);
    }

    /**
     * Introspect a JWT token to retrieve its claims.
     *
     * @param request The introspect request containing the token.
     * @throws AppException if the token is invalid or expired.
     */
    @Override
    public void logout(IntrospectRequest request) {
        try {
            Jwt jwt = jwtDecoder.decode(request.getToken());
            String jti = jwt.getId();
            Date expiration = jwt.getExpiresAt() != null ? Date.from(jwt.getExpiresAt()) : null;
            log.info("Logout success for token: {}", jti);
            log.info("Logout success for token: {}", expiration);
        } catch (JwtException e) {
            log.error("Invalid token during logout: {}", e.getMessage());
            throw new AppException(ErrorMessage.INVALID_TOKEN);
        }
    }

    /**
     * Generate a JWT token for the authenticated user.
     *
     * @param user The authenticated user.
     * @return A JWT token as a string.
     */
    private String generateToken(User user) {
        log.info("Generating JWT token for user: {}", user.getUserName());
        JwsHeader header = JwsHeader.with(SignatureAlgorithm.RS256).build();
        Instant now = Instant.now();
        String r = permissionRep.findDescriptionByRoleId(user.getRole().getRoleId());
        log.info("Role description for user {}: {}", user.getRole().getDescription(), r);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofMinutes(30)))
                .claim("role", user.getRole().getDescription()) // Assuming User has a getRoles() method
                .claim("permission", r)
                .id(UUID.randomUUID().toString())
                .subject(user.getUserName())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }
}
