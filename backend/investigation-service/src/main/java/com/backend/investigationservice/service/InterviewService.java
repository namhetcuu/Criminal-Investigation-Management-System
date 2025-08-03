package com.backend.investigationservice.service;

import com.backend.investigationservice.dto.request.InterviewCreationRequest;
import com.backend.investigationservice.dto.request.InterviewUpdateRequest;
import com.backend.investigationservice.dto.response.InterviewResponse;
import com.backend.investigationservice.dto.response.InvestigationPlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface InterviewService {
    InterviewResponse createInterview(InterviewCreationRequest request);
    List<InterviewResponse> getInterviewsByCaseId(UUID caseId);
    Page<InterviewResponse> findAll(String keyword, Pageable pageable);
    Page<InterviewResponse> getInterviewsByCaseId(UUID caseId, Pageable pageable);
    InterviewResponse getInterviewById(UUID interviewId);
    InterviewResponse updateInterview(UUID interviewId, InterviewUpdateRequest request);
}