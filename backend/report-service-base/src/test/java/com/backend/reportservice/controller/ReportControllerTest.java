package com.backend.reportservice.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import com.backend.reportservice.dto.request.ReportRequest;
import com.backend.reportservice.dto.response.ApiResponse;
import com.backend.reportservice.dto.response.ReportDto;
import com.backend.reportservice.enums.Status;
import com.backend.reportservice.repository.ReportRepository;
import com.backend.reportservice.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportService reportService;

    @MockBean
    private ReportRepository reportRepository;

    @Autowired
    private ApplicationContext context;

    private ReportDto sampleReportDto;
    private ReportRequest sampleReportRequest;
    private String validReportId;

    @BeforeEach
    void setUp() {
        // Sample report ID from data.sql
        validReportId = "550e8400-e29b-41d4-a716-446655440001";

        // Sample ReportDto for mocking
        sampleReportDto = new ReportDto();
        sampleReportDto.setReportId(validReportId);
        sampleReportDto.setCaseId("CASE001");
        sampleReportDto.setTypeReport("Traffic Accident");
        sampleReportDto.setSeverity("High");
        sampleReportDto.setDescription("Car collision at intersection");
        sampleReportDto.setCaseLocation("123 Main St, City A");
        sampleReportDto.setReportedAt(LocalDateTime.of(2025, 7, 13, 8, 30));
        sampleReportDto.setReporterFullname("John Doe");
        sampleReportDto.setReporterEmail("john.doe@example.com");
        sampleReportDto.setReporterPhoneNumber("555-0101");
        sampleReportDto.setStatus("PENDING");
        sampleReportDto.setOfficerApproveUsername("officer1");
        sampleReportDto.setIsDeleted(false);

        // Sample ReportRequest for POST
        sampleReportRequest = ReportRequest.builder()
                .typeReport("THEFT")
                .description("Test Desc")
                .caseLocation("HN")
                .reporterFullname("John")
                .reporterEmail("a@gmail.com")
                .reporterPhoneNumber("0123456789")
                .build();
    }

    @Test
    @WithMockUser(username = "user", authorities = {"REPORT_READ"})
    void getAllReports() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReportDto> page = new PageImpl<>(List.of(sampleReportDto), pageable, 1);
        when(reportService.getAllReports(pageable)).thenReturn(page);

        mockMvc.perform(get("/api/v1/reports")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Retrieved all reports"))
                .andExpect(jsonPath("$.data.content[0].reportId").value(validReportId));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"REPORT_DELETE"})
    void deleteReport() throws Exception {
        doNothing().when(reportService).deleteReport(validReportId);

        mockMvc.perform(delete("/api/v1/reports/" + validReportId)
                        .with(csrf()))
                .andExpect(status().isOk()) // Changed to 200
                .andExpect(jsonPath("$.code").value(204))
                .andExpect(jsonPath("$.message").value("Report deleted successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }



    @Test
    @WithMockUser(username = "user", authorities = {"REPORT_READ"})
    void getAllStatuses() throws Exception {
        // Arrange
        List<String> expectedStatuses = List.of("PENDING", "APPROVED", "REJECTED", "IN_PROGRESS");
        when(reportService.getAllStatuses()).thenReturn(expectedStatuses);

        // Act
        MvcResult result = mockMvc.perform(get("/api/v1/reports/statuses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Retrieved all statuses"))
                .andExpect(jsonPath("$.data", containsInAnyOrder("PENDING", "APPROVED", "REJECTED", "IN_PROGRESS")))
                .andReturn();

        // Debug: Print response body
        System.out.println("Response Body: " + result.getResponse().getContentAsString());

        // Verify mocks
        verify(reportService, times(1)).getAllStatuses();
        verify(reportRepository, never()).findDistinctStatuses();
    }

    @Test
    @WithMockUser(username = "user", authorities = {"REPORT_CREATE"})
    void createReport() throws Exception {
        when(reportService.createReport(any(ReportRequest.class))).thenReturn(sampleReportDto);

        mockMvc.perform(post("/api/v1/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleReportRequest))
                        .with(csrf()))
                .andExpect(status().isOk()) // Changed to 200
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("Report created successfully"))
                .andExpect(jsonPath("$.data.reportId").value(validReportId));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"REPORT_ACCEPT"})
    void acceptReport() throws Exception {
        when(reportService.acceptReport(validReportId)).thenReturn(sampleReportDto);

        mockMvc.perform(put("/api/v1/reports/accept/" + validReportId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Report accepted successfully"))
                .andExpect(jsonPath("$.data.reportId").value(validReportId));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"REPORT_REJECT"})
    void rejectReport() throws Exception {
        when(reportService.acceptReport(validReportId)).thenReturn(sampleReportDto);

        mockMvc.perform(put("/api/v1/reports/reject/" + validReportId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Report rejected successfully"))
                .andExpect(jsonPath("$.data.reportId").value(validReportId));
    }
}