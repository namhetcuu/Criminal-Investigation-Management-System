/*
 * @ (#) GlobalExceptionHandler.java 1.0 7/9/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package com.example.caseservicebase.exception;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/9/2025
 * @version 1.0
 */
import com.example.caseservicebase.dto.responseDTO.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all custom exceptions that extend BaseException.
     * Mô tả: Bắt tất cả các exception tùy chỉnh kế thừa từ BaseException.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    /**
     * Handles validation errors from @Valid annotation.
     * Mô tả: Bắt lỗi validation của Spring (khi dùng @Valid trên DTO).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = "Yêu cầu không hợp lệ. Vui lòng kiểm tra lại dữ liệu.";
        if (ex.getBindingResult().getFieldError() != null) {
            errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other uncaught exceptions as a fallback.
     * Mô tả: Bắt tất cả các lỗi không xác định khác như một phương án cuối cùng (fallback), trả về lỗi 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUncaughtException(Exception ex, HttpServletRequest request) {
        // Log the full stack trace for debugging purposes.
        // Ghi lại log đầy đủ của lỗi để đội backend có thể debug.
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Hệ thống đã gặp lỗi không mong muốn. Vui lòng thử lại sau.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}