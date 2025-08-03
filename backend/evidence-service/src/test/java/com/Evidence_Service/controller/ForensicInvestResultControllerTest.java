package com.Evidence_Service.controller;

import com.Evidence_Service.dto.ForensicInvestResultDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.ForensicInvestResultService;
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

@WebMvcTest(ForensicInvestResultController.class)
class ForensicInvestResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForensicInvestResultService forensicInvestResultService;

    @Autowired
    private ObjectMapper objectMapper;

    private ForensicInvestResultDTO forensicInvestResultDTO;

    @BeforeEach
    void setUp() {
        objectMapper.addMixIn(PageImpl.class, PageImplMixin.class);

        forensicInvestResultDTO = ForensicInvestResultDTO.builder()
                .resultId("result1")
                .evidenceId("evidence1")
                .labName("Test Lab")
                .report("Test report")
                .resultSummary("Test summary")
                .notes("Test notes")
                .imageUrl("http://example.com/image.jpg")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }

    @JsonIgnoreProperties({"pageable"})
    private interface PageImplMixin {
    }

    @Test
    @WithMockUser(authorities = "ADD_FORENSIC_RESULT")
    void createForensicInvestResult_Success() throws Exception {
        when(forensicInvestResultService.addForensicInvestResult(eq("evidence1"), any(ForensicInvestResultDTO.class)))
                .thenReturn(forensicInvestResultDTO);

        mockMvc.perform(post("/api/v1/evidences/evidence1/forensic-invest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(forensicInvestResultDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("Created"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(forensicInvestResultService).addForensicInvestResult(eq("evidence1"), any(ForensicInvestResultDTO.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_FORENSIC_RESULT")
    void getAllForensicInvestResults_Success() throws Exception {
        Page<ForensicInvestResultDTO> page = new PageImpl<>(
                Collections.singletonList(forensicInvestResultDTO),
                PageRequest.of(0, 10),
                1
        );
        when(forensicInvestResultService.getAllForensicInvestByEvidenceId(eq("evidence1"), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/evidences/evidence1/forensic-invest")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched"))
                .andExpect(jsonPath("$.data.content[0].resultId").value("result1"));

        verify(forensicInvestResultService).getAllForensicInvestByEvidenceId(eq("evidence1"), any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_FORENSIC_RESULT")
    void getForensicInvestResultById_Success() throws Exception {
        when(forensicInvestResultService.getForensicInvestById("result1")).thenReturn(forensicInvestResultDTO);

        mockMvc.perform(get("/api/v1/evidences/evidence1/forensic-invest/result1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(forensicInvestResultService).getForensicInvestById("result1");
    }

    @Test
    @WithMockUser(authorities = "EDIT_FORENSIC_RESULT")
    void updateForensicInvestResult_Success() throws Exception {
        when(forensicInvestResultService.updateForensicInvest(eq("evidence1"), eq("result1"), any(ForensicInvestResultDTO.class)))
                .thenReturn(forensicInvestResultDTO);

        mockMvc.perform(put("/api/v1/evidences/evidence1/forensic-invest/result1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(forensicInvestResultDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Updated"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(forensicInvestResultService).updateForensicInvest(eq("evidence1"), eq("result1"), any(ForensicInvestResultDTO.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_FORENSIC_RESULT")
    void deleteForensicInvestResult_Success() throws Exception {
        doNothing().when(forensicInvestResultService).deleteForensicInvestByResultId("result1");

        mockMvc.perform(delete("/api/v1/evidences/evidence1/forensic-invest/result1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Deleted"));

        verify(forensicInvestResultService).deleteForensicInvestByResultId("result1");
    }
}