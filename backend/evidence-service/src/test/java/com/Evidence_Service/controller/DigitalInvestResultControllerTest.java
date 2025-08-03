package com.Evidence_Service.controller;

import com.Evidence_Service.dto.DigitalInvestResultDTO;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.DigitalInvestResultService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DigitalInvestResultController.class)
class DigitalInvestResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DigitalInvestResultService digitalInvestResultService;

    @Autowired
    private ObjectMapper objectMapper;

    private DigitalInvestResultDTO digitalInvestResultDTO;

    @BeforeEach
    void setUp() {
        objectMapper.addMixIn(PageImpl.class, PageImplMixin.class);

        digitalInvestResultDTO = DigitalInvestResultDTO.builder()
                .resultId("result1")
                .evidenceId("evidence1")
                .deviceType("Laptop")
                .analystTool("ToolX")
                .result("Result data")
                .imageUrl("http://example.com/image.jpg")
                .notes("Test notes")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }
    @JsonIgnoreProperties({"pageable"})
    private interface PageImplMixin {
    }

    @Test
    @WithMockUser(authorities = "ADD_DIGITAL_RESULT")
    void createDigitalInvestResult_Success() throws Exception {
        when(digitalInvestResultService.addDigitalInvestResult(eq("evidence1"), any(DigitalInvestResultDTO.class)))
                .thenReturn(digitalInvestResultDTO);

        mockMvc.perform(post("/api/v1/evidences/evidence1/digital-invest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(digitalInvestResultDTO))
                        .with(csrf())) // Thêm token CSRF
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("Digital investigation result created"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(digitalInvestResultService).addDigitalInvestResult(eq("evidence1"), any(DigitalInvestResultDTO.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_DIGITAL_RESULT")
    void getAllDigitalInvestResults_Success() throws Exception {
        Page<DigitalInvestResultDTO> page = new PageImpl<>(Collections.singletonList(digitalInvestResultDTO));
        when(digitalInvestResultService.getAllDigitalInvestByEvidenceId(eq("evidence1"), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/evidences/evidence1/digital-invest")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched digital investigation results"))
                .andExpect(jsonPath("$.data.content[0].resultId").value("result1"));

        verify(digitalInvestResultService).getAllDigitalInvestByEvidenceId(eq("evidence1"), any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_DIGITAL_RESULT")
    void getDigitalInvestResultByResultId_Success() throws Exception {
        when(digitalInvestResultService.getDigitalInvestByResultId("result1")).thenReturn(digitalInvestResultDTO);

        mockMvc.perform(get("/api/v1/evidences/evidence1/digital-invest/result1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched digital investigation result"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(digitalInvestResultService).getDigitalInvestByResultId("result1");
    }

    @Test
    @WithMockUser(authorities = "EDIT_DIGITAL_RESULT")
    void updateDigitalInvestResult_Success() throws Exception {
        when(digitalInvestResultService.updateDigitalInvest(eq("evidence1"), eq("result1"), any(DigitalInvestResultDTO.class)))
                .thenReturn(digitalInvestResultDTO);

        mockMvc.perform(put("/api/v1/evidences/evidence1/digital-invest/result1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(digitalInvestResultDTO))
                        .with(csrf())) // Thêm token CSRF
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Digital investigation result updated"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(digitalInvestResultService).updateDigitalInvest(eq("evidence1"), eq("result1"), any(DigitalInvestResultDTO.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_DIGITAL_RESULT")
    void deleteDigitalInvestResult_Success() throws Exception {
        doNothing().when(digitalInvestResultService).deleteDigitalInvestByResultId("result1");

        mockMvc.perform(delete("/api/v1/evidences/evidence1/digital-invest/result1")
                        .with(csrf())) // Thêm token CSRF
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Digital investigation result deleted"));

        verify(digitalInvestResultService).deleteDigitalInvestByResultId("result1");
    }
}
