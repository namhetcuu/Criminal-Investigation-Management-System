package com.backend.investigationservice.service.serviceImpl;

import com.backend.investigationservice.dto.request.InterviewCreationRequest;
import com.backend.investigationservice.dto.request.InterviewUpdateRequest;
import com.backend.investigationservice.dto.response.InterviewResponse;
import com.backend.investigationservice.mapper.InterviewMapper;
import com.backend.investigationservice.mapper.QuestionMapper;
import com.backend.investigationservice.entity.Interview;
import com.backend.investigationservice.entity.Question;
import com.backend.investigationservice.repository.InterviewRepository;
import com.backend.investigationservice.repository.QuestionRepository;
import com.backend.investigationservice.service.InterviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final QuestionRepository questionRepository;

    @Override
    public InterviewResponse createInterview(InterviewCreationRequest request) {
        // 1. Map DTO → Entity
        Interview interview = InterviewMapper.toEntity(request);

        // 2. Save Interview
        interview = interviewRepository.save(interview);

        // 3. Map and Save Questions
        List<Question> questions = QuestionMapper.toEntities(request.getQuestions(), interview.getInterviewId());
        questionRepository.saveAll(questions);

        // 4. Return Response
        return InterviewMapper.toResponse(interview, questions);
    }

    @Override
    public List<InterviewResponse> getInterviewsByCaseId(UUID caseId) {
        List<Interview> interviews = interviewRepository.findByCaseIdAndIsDeletedFalse(caseId);

        return interviews.stream().map(interview -> {
            List<Question> questions = questionRepository.findByInterviewIdAndIsDeletedFalse(interview.getInterviewId());
            return InterviewMapper.toResponse(interview, questions);
        }).toList();
    }
    @Override
    public Page<InterviewResponse> getInterviewsByCaseId(UUID caseId, Pageable pageable) {
        Page<Interview> interviews = interviewRepository.findByCaseIdAndIsDeletedFalse(caseId, pageable);

        return interviews.map(interview -> {
            List<Question> questions = questionRepository.findByInterviewIdAndIsDeletedFalse(interview.getInterviewId());
            return InterviewMapper.toResponse(interview, questions);
        });
    }

    @Override
    public InterviewResponse getInterviewById(UUID interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new IllegalArgumentException("Interview not found"));

        List<Question> questions = questionRepository.findByInterviewIdAndIsDeletedFalse(interviewId);

        return InterviewMapper.toResponse(interview, questions);
    }

    @Override
    public InterviewResponse updateInterview(UUID interviewId, InterviewUpdateRequest request) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new IllegalArgumentException("Interview not found"));

        // Update fields
        interview.setLocation(request.getLocation());
        interview.setStartTime(request.getStartTime());
        interview.setEndTime(request.getEndTime());
        interview.setHolidayConflict(request.getHolidayConflict());
        interview.setHolidayId(request.getHolidayId());
        interview.setIntervieweeType(request.getIntervieweeType());
        interview.setIntervieweeId(request.getIntervieweeId());
        interview.setIntervieweeName(request.getIntervieweeName());
        interview.setAttachedFiles(request.getAttachedFiles());
        interview = interviewRepository.save(interview);
        // Update Questions
        List<Question> existingQuestions = questionRepository.findByInterviewIdAndIsDeletedFalse(interviewId);
        List<Question> updatedQuestions = QuestionMapper.toEntities(request.getQuestions(), interviewId);
        // Delete old questions
        existingQuestions.forEach(question -> {
            question.setDeleted(true);
            questionRepository.save(question);
        });
        // Save new questions
        questionRepository.saveAll(updatedQuestions);
        // Return updated interview response
        return InterviewMapper.toResponse(interview, updatedQuestions);
    }

    @Override
    public Page<InterviewResponse> findAll(String keyword, Pageable pageable) {
        Specification<Interview> spec = (root, query, cb) -> {
            Predicate predicate = cb.isFalse(root.get("isDeleted"));

            if (keyword != null && !keyword.isBlank()) {
                Predicate locationPredicate = cb.like(cb.lower(root.get("location")), "%" + keyword.toLowerCase() + "%");
                predicate = cb.and(predicate, locationPredicate);
            }

            return predicate;
        };

        Page<Interview> interviews = interviewRepository.findAll(spec, pageable);

        return interviews.map(interview -> {
            List<Question> questions = questionRepository.findByInterviewIdAndIsDeletedFalse(interview.getInterviewId());
            return InterviewMapper.toResponse(interview, questions);
        });
    }
}