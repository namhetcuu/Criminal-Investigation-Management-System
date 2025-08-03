/*
 * @ (#) ErrorMessage.java  1.0 7/7/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.commonservice.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/7/2025
 * @version:    1.0
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorMessage {
    UNAUTHORIZED("Bạn không có quyền truy cập tài nguyên này", 403, HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED( "Unauthenticated",403, HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("Invalid token", 401, HttpStatus.UNAUTHORIZED),
    INVALID_DATA("Validation failed", 400, HttpStatus.BAD_REQUEST),
    SUSPECT_STATUS_NOT_FOUND("Suspect status not found", 404, HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("User not found", 404, HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTS("Username already exists", 409, HttpStatus.CONFLICT),
    PERMISSION_ALREADY_EXISTS("Permission already exists", 409, HttpStatus.CONFLICT),
    PERMISSION_NOT_FOUND("Permission not found", 404, HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("Role not found", 404, HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("username or password invalid", 401, HttpStatus.UNAUTHORIZED),
    ROLE_ALREADY_EXISTS("Role already exists", 409, HttpStatus.CONFLICT),
    ;
    String message;
    int code;
    HttpStatus httpStatus;
}
