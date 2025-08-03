package com.backend.investigationservice.repository;

import com.backend.investigationservice.entity.WitnessInterview;
import com.backend.investigationservice.entity.WitnessInterviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface WitnessInterviewRepository extends JpaRepository<WitnessInterview, WitnessInterviewId> {
    List<WitnessInterview> findByInterview_InterviewId(UUID interviewId);
}
