package com.backend.investigationservice.mapper;

import com.backend.investigationservice.dto.request.InterviewCreationRequest;
import com.backend.investigationservice.dto.response.InterviewResponse;
import com.backend.investigationservice.dto.response.QuestionResponse;
import com.backend.investigationservice.entity.Interview;
import com.backend.investigationservice.entity.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InterviewMapper {

    public static Interview toEntity(InterviewCreationRequest request) {
        if (request == null) return null;

        Interview interview = new Interview();
        interview.setCaseId(request.getCaseId());
        interview.setLocation(request.getLocation());
        interview.setStartTime(request.getStartTime());
        interview.setEndTime(request.getEndTime());
        interview.setHolidayConflict(request.getHolidayConflict());
        interview.setHolidayId(request.getHolidayId());
        interview.setIntervieweeType(request.getIntervieweeType());
        interview.setIntervieweeId(request.getIntervieweeId());
        interview.setIntervieweeName(request.getIntervieweeName());
        interview.setAttachedFiles(request.getAttachedFiles());
        interview.setDeleted(false); // default value
        return interview;
    }

    public static InterviewResponse toResponse(Interview interview, List<Question> questions) {
        if (interview == null) return null;

        List<QuestionResponse> questionResponses = QuestionMapper.toResponses(questions);

        return InterviewResponse.builder()
                .interviewId(interview.getInterviewId())
                .caseId(interview.getCaseId())
                .location(interview.getLocation())
                .startTime(interview.getStartTime())
                .endTime(interview.getEndTime())
                .holidayConflict(interview.getHolidayConflict())
                .holidayId(interview.getHolidayId())
                .deleted(interview.isDeleted())
                .intervieweeType(interview.getIntervieweeType())
                .intervieweeId(interview.getIntervieweeId())
                .intervieweeName(interview.getIntervieweeName())
                .attachedFiles(interview.getAttachedFiles())
                .questions(questionResponses)
                .build();
    }
}