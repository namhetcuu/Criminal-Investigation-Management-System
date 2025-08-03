package com.Evidence_Service.controller;

import com.Evidence_Service.dto.EvidenceDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.EvidenceService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EvidenceController.class)
class EvidenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvidenceService evidenceService;

    @Autowired
    private ObjectMapper objectMapper;

    private EvidenceDTO evidenceDTO;

    @BeforeEach
    void setUp() {
        objectMapper.addMixIn(PageImpl.class, PageImplMixin.class);

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
    }

    @JsonIgnoreProperties({"pageable"})
    private interface PageImplMixin {
    }

    @Test
    @WithMockUser(authorities = "ADD_EVIDENCE")
    void createEvidence_Success() throws Exception {
        when(evidenceService.createEvidence(any(EvidenceDTO.class))).thenReturn(evidenceDTO);

        mockMvc.perform(post("/api/v1/evidences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evidenceDTO))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("Created evidence"))
                .andExpect(jsonPath("$.data.evidenceId").value("evidence1"));

        verify(evidenceService).createEvidence(any(EvidenceDTO.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_EVIDENCE")
    void getByEvidenceId_Success() throws Exception {
        when(evidenceService.getByEvidenceId("evidence1")).thenReturn(evidenceDTO);

        mockMvc.perform(get("/api/v1/evidences/evidence1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Evidence found"))
                .andExpect(jsonPath("$.data.evidenceId").value("evidence1"));

        verify(evidenceService).getByEvidenceId("evidence1");
    }

    @Test
    @WithMockUser(authorities = "VIEW_EVIDENCE")
    void getByEvidenceIds_Success() throws Exception {
        when(evidenceService.getByEvidenceIds(Collections.singletonList("evidence1")))
                .thenReturn(Collections.singletonList(evidenceDTO));

        mockMvc.perform(get("/api/v1/evidences/by-ids")
                        .param("ids", "evidence1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Evidence found"))
                .andExpect(jsonPath("$.data[0].evidenceId").value("evidence1"));

        verify(evidenceService).getByEvidenceIds(Collections.singletonList("evidence1"));
    }

    @Test
    @WithMockUser(authorities = "VIEW_EVIDENCE")
    void getByCaseOrSuspect_Success() throws Exception {
        Page<EvidenceDTO> page = new PageImpl<>(
                Collections.singletonList(evidenceDTO),
                PageRequest.of(0, 10),
                1
        );
        when(evidenceService.getAllEvidence(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/evidences")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("List evidence by case or suspect"))
                .andExpect(jsonPath("$.data.content[0].evidenceId").value("evidence1"));

        verify(evidenceService).getAllEvidence(any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_EVIDENCE")
    void updateEvidence_Success() throws Exception {
        when(evidenceService.updateEvidence(any(EvidenceDTO.class))).thenReturn(evidenceDTO);

        mockMvc.perform(put("/api/v1/evidences/evidence1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evidenceDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Updated evidence"))
                .andExpect(jsonPath("$.data.evidenceId").value("evidence1"));

        verify(evidenceService).updateEvidence(any(EvidenceDTO.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_EVIDENCE")
    void deleteByEvidenceId_Success() throws Exception {
        doNothing().when(evidenceService).deleteByEvidenceId("evidence1");

        mockMvc.perform(delete("/api/v1/evidences/evidence1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Deleted evidence"));

        verify(evidenceService).deleteByEvidenceId("evidence1");
    }
}