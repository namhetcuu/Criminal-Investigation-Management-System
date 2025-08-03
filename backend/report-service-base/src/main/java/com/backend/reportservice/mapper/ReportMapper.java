package com.backend.reportservice.mapper;

import com.backend.reportservice.dto.response.ReportDto;
import com.backend.reportservice.dto.request.ReportRequest;
import com.backend.reportservice.entity.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportDto toDto(Report report);
    Report toEntity(ReportDto reportDto);
    Report createReport(ReportRequest reportRequest);
}