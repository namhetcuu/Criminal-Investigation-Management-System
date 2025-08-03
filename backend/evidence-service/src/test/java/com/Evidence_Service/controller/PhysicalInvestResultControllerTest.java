package com.Evidence_Service.controller;

import com.Evidence_Service.dto.PhysicalInvestResultDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.PhysicalInvestResultService;
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

@WebMvcTest(PhysicalInvestResultController.class)
class PhysicalInvestResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhysicalInvestResultService physicalInvestResultService;

    @Autowired
    private ObjectMapper objectMapper;

    private PhysicalInvestResultDTO physicalInvestResultDTO;

    @BeforeEach
    void setUp() {
        objectMapper.addMixIn(PageImpl.class, PageImplMixin.class);

        physicalInvestResultDTO = PhysicalInvestResultDTO.builder()
                .resultId("result1")
                .evidenceId("evidence1")
                .status("Processed")
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
    @WithMockUser(authorities = "ADD_PHYSICAL_RESULT")
    void createPhysicalInvestResult_Success() throws Exception {
        when(physicalInvestResultService.addPhysicalInvestResult(eq("evidence1"), any(PhysicalInvestResultDTO.class)))
                .thenReturn(physicalInvestResultDTO);

        mockMvc.perform(post("/api/v1/evidences/evidence1/physical-invest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(physicalInvestResultDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("Created"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(physicalInvestResultService).addPhysicalInvestResult(eq("evidence1"), any(PhysicalInvestResultDTO.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_PHYSICAL_RESULT")
    void getAllPhysicalInvestResults_Success() throws Exception {
        Page<PhysicalInvestResultDTO> page = new PageImpl<>(
                Collections.singletonList(physicalInvestResultDTO),
                PageRequest.of(0, 10),
                1
        );
        when(physicalInvestResultService.getAllPhysicalInvestByEvidenceId(eq("evidence1"), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/evidences/evidence1/physical-invest")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched"))
                .andExpect(jsonPath("$.data.content[0].resultId").value("result1"));

        verify(physicalInvestResultService).getAllPhysicalInvestByEvidenceId(eq("evidence1"), any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_PHYSICAL_RESULT")
    void getPhysicalInvestResultById_Success() throws Exception {
        when(physicalInvestResultService.getPhysicalInvestByResultId("result1")).thenReturn(physicalInvestResultDTO);

        mockMvc.perform(get("/api/v1/evidences/evidence1/physical-invest/result1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(physicalInvestResultService).getPhysicalInvestByResultId("result1");
    }

    @Test
    @WithMockUser(authorities = "EDIT_PHYSICAL_RESULT")
    void updatePhysicalInvestResult_Success() throws Exception {
        when(physicalInvestResultService.updatePhysicalInvest(eq("evidence1"), eq("result1"), any(PhysicalInvestResultDTO.class)))
                .thenReturn(physicalInvestResultDTO);

        mockMvc.perform(put("/api/v1/evidences/evidence1/physical-invest/result1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(physicalInvestResultDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Updated"))
                .andExpect(jsonPath("$.data.resultId").value("result1"));

        verify(physicalInvestResultService).updatePhysicalInvest(eq("evidence1"), eq("result1"), any(PhysicalInvestResultDTO.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_PHYSICAL_RESULT")
    void deletePhysicalInvestResult_Success() throws Exception {
        doNothing().when(physicalInvestResultService).deletePhysicalInvestByResultId("result1");

        mockMvc.perform(delete("/api/v1/evidences/evidence1/physical-invest/result1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Deleted"));

        verify(physicalInvestResultService).deletePhysicalInvestByResultId("result1");
    }
}