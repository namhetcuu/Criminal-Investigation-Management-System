package com.backend.investigationservice.mapper;
import com.backend.investigationservice.dto.request.QuestionCreationRequest;
import com.backend.investigationservice.dto.response.QuestionResponse;
import com.backend.investigationservice.entity.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
public class QuestionMapper {
    public static Question toEntity(QuestionCreationRequest request, UUID interviewId) {
        if (request == null) return null;

        Question question = new Question();
        question.setContent(request.getContent());
        question.setAnswer(request.getAnswer());
        question.setReliability(request.getReliability());
        question.setInterviewId(interviewId);
        question.setDeleted(false);
        return question;
    }

    public static List<Question> toEntities(List<QuestionCreationRequest> requests, UUID interviewId) {
        if (requests == null) return null;
        return requests.stream()
                .map(r -> toEntity(r, interviewId))
                .collect(Collectors.toList());
    }

    public static QuestionResponse toResponse(Question entity) {
        if (entity == null) return null;

        return QuestionResponse.builder()
                .content(entity.getContent())
                .answer(entity.getAnswer())
                .reliability(entity.getReliability())
                .build();
    }

    public static List<QuestionResponse> toResponses(List<Question> questions) {
        if (questions == null) return null;
        return questions.stream()
                .map(QuestionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
