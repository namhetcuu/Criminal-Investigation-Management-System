/*
 * @ (#) QuestionServiceImpl.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.investigationservice.service.serviceImpl;

import com.backend.investigationservice.dto.response.QuestionResponse;
import com.backend.investigationservice.mapper.QuestionMapper;
import com.backend.investigationservice.entity.Question;
import com.backend.investigationservice.repository.InterviewRepository;
import com.backend.investigationservice.repository.QuestionRepository;
import com.backend.investigationservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;

    @Override
    public Page<QuestionResponse> findAll(Pageable pageable) {
        // Trả về các câu hỏi chưa bị xóa (isDeleted = false)
        return questionRepository.findAll((root, query, cb) ->
                        cb.isFalse(root.get("isDeleted")), pageable)
                .map(QuestionMapper::toResponse);
    }

    @Override
    public void deleteQuestion(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        question.setDeleted(true);
        questionRepository.save(question);
    }

}
