package com.Evidence_Service.controller;

import com.Evidence_Service.dto.CaseDTO;
import com.Evidence_Service.dto.EvidenceDTO;
import com.Evidence_Service.dto.SuspectDTO;
import com.Evidence_Service.dto.WarrantDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.EvidenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EvidenceAssignmentController.class)
class EvidenceAssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvidenceService evidenceService;

    @Autowired
    private ObjectMapper objectMapper;

    private EvidenceDTO evidenceDTO;
    private SuspectDTO suspectDTO;
    private CaseDTO caseDTO;
    private WarrantDTO warrantDTO;

    @BeforeEach
    void setUp() {
        evidenceDTO = EvidenceDTO.builder()
                .evidenceId("evidence1")
                .description("Test evidence")
                .detailedDescription("Detailed test evidence")
                .attachedFile("file.pdf")
                .initialCondition("Good")
                .preservationMeasures("Stored")
                .locationAtScene("Scene1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .measureSurveyId("survey1")
                .investigationPlanId("plan1")
                .reportId("report1")
                .collectorUsername("user1")
                .isDeleted(false)
                .build();

        suspectDTO = new SuspectDTO();
        suspectDTO.setSuspectId("suspect1");

        caseDTO = CaseDTO.builder()
                .caseId("case1")
                .caseNumber("CASE001")
                .status("Open")
                .summary("Test case")
                .severity("High")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        warrantDTO = new WarrantDTO();
        warrantDTO.setWarrantId("warrant1");
    }

    @Test
    @WithMockUser(authorities = "ASSIGN_SUSPECT")
    void assignSuspect_Success() throws Exception {
        when(evidenceService.assignSuspect(eq("evidence1"), any(SuspectDTO.class))).thenReturn(evidenceDTO);

        mockMvc.perform(put("/api/v1/evidences/evidence1/assign-suspect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(suspectDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Assigned suspect"))
                .andExpect(jsonPath("$.data.evidenceId").value("evidence1"));

        verify(evidenceService).assignSuspect(eq("evidence1"), any(SuspectDTO.class));
    }

    @Test
    @WithMockUser(authorities = "ASSIGN_CASE")
    void assignCase_Success() throws Exception {
        when(evidenceService.assignCase(eq("evidence1"), any(CaseDTO.class))).thenReturn(evidenceDTO);

        mockMvc.perform(put("/api/v1/evidences/evidence1/assign-case")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(caseDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Assigned case"))
                .andExpect(jsonPath("$.data.evidenceId").value("evidence1"));

        verify(evidenceService).assignCase(eq("evidence1"), any(CaseDTO.class));
    }

    @Test
    @WithMockUser(authorities = "ASSIGN_WARRANT")
    void assignWarrant_Success() throws Exception {
        when(evidenceService.assignWarrant(eq("evidence1"), any(WarrantDTO.class))).thenReturn(evidenceDTO);

        mockMvc.perform(put("/api/v1/evidences/evidence1/assign-warrant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warrantDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Assigned warrant"))
                .andExpect(jsonPath("$.data.evidenceId").value("evidence1"));

        verify(evidenceService).assignWarrant(eq("evidence1"), any(WarrantDTO.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_EVIDENCE")
    void getSuspectsByEvidence_Success() throws Exception {
        when(evidenceService.getSuspectsByEvidence("evidence1")).thenReturn(Collections.singletonList(suspectDTO));

        mockMvc.perform(get("/api/v1/evidences/evidence1/suspects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Suspects fetched"))
                .andExpect(jsonPath("$.data[0].suspectId").value("suspect1"));

        verify(evidenceService).getSuspectsByEvidence("evidence1");
    }

    @Test
    @WithMockUser(authorities = "VIEW_EVIDENCE")
    void getWarrantsByEvidence_Success() throws Exception {
        when(evidenceService.getWarrantsByEvidence("evidence1")).thenReturn(Collections.singletonList(warrantDTO));

        mockMvc.perform(get("/api/v1/evidences/evidence1/warrants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Warrants fetched"))
                .andExpect(jsonPath("$.data[0].warrantId").value("warrant1"));

        verify(evidenceService).getWarrantsByEvidence("evidence1");
    }

    @Test
    @WithMockUser(authorities = "VIEW_EVIDENCE")
    void getCasesByEvidence_Success() throws Exception {
        when(evidenceService.getCasesByEvidence("evidence1")).thenReturn(Collections.singletonList(caseDTO));

        mockMvc.perform(get("/api/v1/evidences/evidence1/cases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Cases fetched"))
                .andExpect(jsonPath("$.data[0].caseId").value("case1"));

        verify(evidenceService).getCasesByEvidence("evidence1");
    }
}