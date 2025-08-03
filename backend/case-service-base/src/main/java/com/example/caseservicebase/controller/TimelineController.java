/*
 * @ (#) TimelineController.java 1.0 7/10/2025 09:24 PM +07
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package com.example.caseservicebase.controller;

import com.example.caseservicebase.dto.requestDTO.TimelineRequestDTO;
import com.example.caseservicebase.dto.responseDTO.ResponseData;
import com.example.caseservicebase.exception.InvalidRequestException;
import com.example.caseservicebase.exception.ResourceNotFoundException;
import com.example.caseservicebase.model.Timeline;
import com.example.caseservicebase.service.TimelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description Controller xử lý các yêu cầu liên quan đến entity Timeline.
 * @author : Nguyen Truong An
 * @date : 7/10/2025 09:24 PM +07
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/timeline")
public class TimelineController {

    private final TimelineService timelineService;

    @PostMapping("")
    public ResponseData<Long> createTimeline(@RequestBody TimelineRequestDTO request) {
        log.info("Request to create timeline, timelineId={}", request.getTimelineId());
        try {
            if (request.getTimelineId() == null) {
                throw new InvalidRequestException("Timeline ID cannot be null");
            }
            if (request.getCaseResultId() != null && request.getCaseResultId() <= 0) {
                throw new InvalidRequestException("Case Result ID must be a positive value if provided");
            }
            if (request.getHolidayId() != null && request.getHolidayId() <= 0) {
                throw new InvalidRequestException("Holiday ID must be a positive value if provided");
            }
            Long timelineId = timelineService.createTimeline(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Timeline created successfully", timelineId);
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to create timeline due to invalid request");
        }
    }

    @GetMapping("/{id}")
    public ResponseData<Timeline> getTimelineById(@PathVariable Long id) {
        log.info("Request to retrieve timeline, timelineId={}", id);
        try {
            if (id == null) {
                throw new InvalidRequestException("Timeline ID cannot be null");
            }
            Timeline timeline = timelineService.getTimelineById(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Timeline retrieved successfully", timeline);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to retrieve timeline due to invalid request");
        }
    }

    @GetMapping("")
    public ResponseData<List<Timeline>> getAllTimelines() {
        log.info("Request to retrieve all timelines");
        try {
            List<Timeline> timelines = timelineService.getAllTimelines();
            return new ResponseData<>(HttpStatus.OK.value(), "Timelines retrieved successfully", timelines);
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to retrieve timelines due to an error");
        }
    }

    @PutMapping("/{id}")
    public ResponseData<Timeline> updateTimeline(@PathVariable Long id, @RequestBody TimelineRequestDTO request) {
        log.info("Request to update timeline, timelineId={}", id);
        try {
            if (id == null) {
                throw new InvalidRequestException("Timeline ID cannot be null");
            }
            if (request.getCaseResultId() != null && request.getCaseResultId() <= 0) {
                throw new InvalidRequestException("Case Result ID must be a positive value if provided");
            }
            if (request.getHolidayId() != null && request.getHolidayId() <= 0) {
                throw new InvalidRequestException("Holiday ID must be a positive value if provided");
            }
            Timeline updatedTimeline = timelineService.updateTimeline(id, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Timeline updated successfully", updatedTimeline);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to update timeline due to invalid request");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> softDeleteTimeline(@PathVariable Long id) {
        log.info("Request to soft delete timeline, timelineId={}", id);
        try {
            if (id == null) {
                throw new InvalidRequestException("Timeline ID cannot be null");
            }
            timelineService.softDeleteTimeline(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Timeline soft deleted successfully", null);
        } catch (ResourceNotFoundException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (InvalidRequestException e) {
            throw e; // Được xử lý bởi GlobalExceptionHandler
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to soft delete timeline due to invalid request");
        }
    }
}