package com.example.caseservicebase.repository;

import com.example.caseservicebase.model.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {
    Optional<Timeline> findByTimelineIdAndIsDeletedFalse(Long timelineId);
    List<Timeline> findAllByIsDeletedFalse();
}