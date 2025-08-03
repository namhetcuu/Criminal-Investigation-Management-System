/*
 * @ (#) QuestionService.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.investigationservice.service;


import com.backend.investigationservice.dto.response.QuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    Page<QuestionResponse> findAll(Pageable pageable);
    void deleteQuestion(UUID questionId);
}
