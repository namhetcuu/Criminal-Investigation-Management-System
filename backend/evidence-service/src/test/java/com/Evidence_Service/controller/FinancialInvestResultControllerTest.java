package com.Evidence_Service.controller;

import com.Evidence_Service.dto.FinancialInvestResultDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.FinancialInvestResultService;
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

@WebMvcTest(FinancialInvestResultController.class)
class FinancialInvestResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialInvestResultService financialInvestResultService;

    @Autowired
    private ObjectMapper objectMapper;

    private FinancialInvestResultDTO financialInvestResultDTO;

    @BeforeEach
    void setUp() {
        objectMapper.addMixIn(PageImpl.class, PageImplMixin.class);

        financialInvestResultDTO = FinancialInvestResultDTO.builder()
                .resultId("result1")
                .evidenceId("evidence1")
                .summary("Test summary")
                .attachedFile("file.pdf")
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
    @WithMockUser(authorities = "ADD_FINANCIAL_RESULT")
    void createFinancialInvestResult_Success() throws Exception {
        when(financialInvestResultService.addFinancialInvestResult(eq("evidence1"), any(FinancialInvestResultDTO.class)))
                .thenReturn(financialInvestResultDTO);

        mockMvc.perform(post("/api/v1/evidences/evidence1/financial-invest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(financialInvestResultDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("Created"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(financialInvestResultService).addFinancialInvestResult(eq("evidence1"), any(FinancialInvestResultDTO.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_FINANCIAL_RESULT")
    void getAllFinancialInvestResults_Success() throws Exception {
        Page<FinancialInvestResultDTO> page = new PageImpl<>(
                Collections.singletonList(financialInvestResultDTO),
                PageRequest.of(0, 10),
                1
        );
        when(financialInvestResultService.getAllFinancialInvestByEvidenceId(eq("evidence1"), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/evidences/evidence1/financial-invest")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Financial Invest Fetched"))
                .andExpect(jsonPath("$.data.content[0].resultId").value("result1"));

        verify(financialInvestResultService).getAllFinancialInvestByEvidenceId(eq("evidence1"), any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_FINANCIAL_RESULT")
    void getFinancialInvestResultById_Success() throws Exception {
        when(financialInvestResultService.getFinancialInvestById("result1")).thenReturn(financialInvestResultDTO);

        mockMvc.perform(get("/api/v1/evidences/evidence1/financial-invest/result1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(financialInvestResultService).getFinancialInvestById("result1");
    }

    @Test
    @WithMockUser(authorities = "EDIT_FINANCIAL_RESULT")
    void updateFinancialInvestResult_Success() throws Exception {
        when(financialInvestResultService.updateFinancialInvest(eq("evidence1"), eq("result1"), any(FinancialInvestResultDTO.class)))
                .thenReturn(financialInvestResultDTO);

        mockMvc.perform(put("/api/v1/evidences/evidence1/financial-invest/result1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(financialInvestResultDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Updated"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(financialInvestResultService).updateFinancialInvest(eq("evidence1"), eq("result1"), any(FinancialInvestResultDTO.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_FINANCIAL_RESULT")
    void deleteFinancialInvestResult_Success() throws Exception {
        doNothing().when(financialInvestResultService).deleteFinancialInvestByResultId("result1");

        mockMvc.perform(delete("/api/v1/evidences/evidence1/financial-invest/result1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Deleted"));

        verify(financialInvestResultService).deleteFinancialInvestByResultId("result1");
    }
}