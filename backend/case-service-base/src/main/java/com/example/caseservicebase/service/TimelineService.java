package com.example.caseservicebase.service;

import com.example.caseservicebase.dto.requestDTO.TimelineRequestDTO;
import com.example.caseservicebase.model.Timeline;

import java.util.List;

public interface TimelineService {
    Long createTimeline(TimelineRequestDTO request);
    Timeline getTimelineById(Long id);
    List<Timeline> getAllTimelines();
    Timeline updateTimeline(Long id, TimelineRequestDTO request);
    void softDeleteTimeline(Long id);
}