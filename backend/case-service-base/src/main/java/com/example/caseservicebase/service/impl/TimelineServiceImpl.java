package com.example.caseservicebase.service.impl;

import com.example.caseservicebase.dto.requestDTO.TimelineRequestDTO;
import com.example.caseservicebase.model.CaseResult;
import com.example.caseservicebase.model.Timeline;
import com.example.caseservicebase.repository.TimelineRepository;
import com.example.caseservicebase.service.TimelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimelineServiceImpl implements TimelineService {

    private final TimelineRepository timelineRepository;

    @Override
    public Long createTimeline(TimelineRequestDTO request) {
        // Kiểm tra timelineId
        if (request.getTimelineId() == null || request.getTimelineId() <= 0) {
            throw new IllegalArgumentException("Timeline ID must be a positive non-null value");
        }

        // Kiểm tra holidayId
        if (request.getHolidayId() != null && request.getHolidayId() <= 0) {
            throw new IllegalArgumentException("Holiday ID must be a positive value if provided");
        }

        // Kiểm tra caseResultId
        if (request.getCaseResultId() != null && request.getCaseResultId() <= 0) {
            throw new IllegalArgumentException("Case Result ID must be a positive value if provided");
        }

        Timeline timeline = Timeline.builder()
                .timelineId(request.getTimelineId())
                .holidayId(request.getHolidayId())
                .startTime(request.getStartTime() != null ? LocalDateTime.parse(request.getStartTime()) : null)
                .endTime(request.getEndTime() != null ? LocalDateTime.parse(request.getEndTime()) : null)
                .attachedFile(request.getAttachedFile())
                .notes(request.getNotes())
                .caseResultId(request.getCaseResultId() != null ? new CaseResult(request.getCaseResultId(), null, null, null, null, null, null, false) : null)
                .activity(request.getActivity())
                .holidayConflict(request.getHolidayConflict())
                .isDeleted(false)
                .build();

        log.info("Created timeline successfully, timelineId={}", timeline.getTimelineId());
        return timelineRepository.save(timeline).getTimelineId();
    }

    @Override
    public Timeline getTimelineById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Timeline ID must be a positive non-null value");
        }
        Timeline timeline = timelineRepository.findByTimelineIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Timeline not found with id = " + id));
        log.info("Retrieved timeline successfully, timelineId={}", id);
        return timeline;
    }

    @Override
    public List<Timeline> getAllTimelines() {
        List<Timeline> timelines = timelineRepository.findAllByIsDeletedFalse();
        log.info("Retrieved {} timelines", timelines.size());
        return timelines;
    }

    @Override
    public Timeline updateTimeline(Long id, TimelineRequestDTO request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Timeline ID must be a positive non-null value");
        }

        // Kiểm tra holidayId
        if (request.getHolidayId() != null && request.getHolidayId() <= 0) {
            throw new IllegalArgumentException("Holiday ID must be a positive value if provided");
        }

        // Kiểm tra caseResultId
        if (request.getCaseResultId() != null && request.getCaseResultId() <= 0) {
            throw new IllegalArgumentException("Case Result ID must be a positive value if provided");
        }

        Timeline timeline = timelineRepository.findByTimelineIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Timeline not found with id = " + id));

        if (request.getHolidayId() != null) {
            timeline.setHolidayId(request.getHolidayId());
        }
        if (request.getStartTime() != null) {
            timeline.setStartTime(LocalDateTime.parse(request.getStartTime()));
        }
        if (request.getEndTime() != null) {
            timeline.setEndTime(LocalDateTime.parse(request.getEndTime()));
        }
        if (request.getAttachedFile() != null) {
            timeline.setAttachedFile(request.getAttachedFile());
        }
        if (request.getNotes() != null) {
            timeline.setNotes(request.getNotes());
        }
        if (request.getCaseResultId() != null) {
            timeline.setCaseResultId(new CaseResult(request.getCaseResultId(), null, null, null, null, null, null, false));
        }
        if (request.getActivity() != null) {
            timeline.setActivity(request.getActivity());
        }
        if (request.getHolidayConflict() != null) {
            timeline.setHolidayConflict(request.getHolidayConflict());
        }

        log.info("Updated timeline successfully, timelineId={}", id);
        return timelineRepository.save(timeline);
    }

    @Override
    public void softDeleteTimeline(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Timeline ID must be a positive non-null value");
        }
        Timeline timeline = timelineRepository.findByTimelineIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Timeline not found with id = " + id));
        timeline.setIsDeleted(true);
        timelineRepository.save(timeline);
        log.info("Soft deleted timeline successfully, timelineId={}", id);
    }
}