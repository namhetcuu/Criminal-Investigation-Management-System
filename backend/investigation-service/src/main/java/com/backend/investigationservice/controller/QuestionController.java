/*
 * @ (#) QuestionController.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.investigationservice.controller;

import com.backend.investigationservice.dto.response.QuestionResponse;
import com.backend.investigationservice.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Question", description = "Question API")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    @Operation(summary = "Get all Questions", description = "Retrieve all non-deleted questions")
    @ApiResponse(responseCode = "200", description = "Questions retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No questions found")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<Page<QuestionResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(questionService.findAll(pageable));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Question", description = "Delete a question by ID")
    @ApiResponse(responseCode = "204", description = "Question deleted successfully")
    @ApiResponse(responseCode = "404", description = "Question not found")
    @ApiResponse(responseCode = "400", description = "Invalid question ID")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
