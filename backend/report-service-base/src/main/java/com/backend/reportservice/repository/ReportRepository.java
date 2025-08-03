package com.backend.reportservice.repository;

import com.backend.reportservice.dto.response.ReportDto;
import com.backend.reportservice.entity.Report;
import com.backend.reportservice.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    @Query("SELECT DISTINCT r.status FROM Report r")
    List<Status> findDistinctStatuses();

    Page<Report> findAll(Pageable pageable);
}