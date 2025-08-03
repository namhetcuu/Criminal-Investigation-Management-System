package com.Evidence_Service.controller;

import com.Evidence_Service.dto.RecordInfoDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.RecordInfoService;
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

@WebMvcTest(RecordInfoController.class)
class RecordInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecordInfoService recordInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    private RecordInfoDTO recordInfoDTO;

    @BeforeEach
    void setUp() {
        objectMapper.addMixIn(PageImpl.class, PageImplMixin.class);

        recordInfoDTO = RecordInfoDTO.builder()
                .recordInfoId("record1")
                .evidenceId("evidence1")
                .typeName("Image")
                .source("Camera")
                .dateCollected(LocalDateTime.now())
                .summary("Test summary")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }

    @JsonIgnoreProperties({"pageable"})
    private interface PageImplMixin {
    }

    @Test
    @WithMockUser(authorities = "ADD_RECORD_INFO")
    void createRecordInfo_Success() throws Exception {
        when(recordInfoService.createRecordInfo(any(RecordInfoDTO.class))).thenReturn(recordInfoDTO);

        mockMvc.perform(post("/api/v1/record-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordInfoDTO))
                        .with(csrf()))
                .andExpect(status().isCreated()) // Expect HTTP 201
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("Record info created"))
                .andExpect(jsonPath("$.data.recordInfoId").value("record1"));

        verify(recordInfoService).createRecordInfo(any(RecordInfoDTO.class));
    }

    @Test
    @WithMockUser(authorities = "ADD_RECORD_INFO")
    void createRecordInfo_InvalidDTO_Fails() throws Exception {
        RecordInfoDTO invalidDTO = RecordInfoDTO.builder()
                .recordInfoId("record1")
                .evidenceId("") // Invalid: empty evidenceId
                .typeName("Image")
                .source("Camera")
                .dateCollected(LocalDateTime.now())
                .summary("Test summary")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();

        mockMvc.perform(post("/api/v1/record-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.data.evidenceId").value("Evidence ID cannot be empty"));

        verify(recordInfoService, never()).createRecordInfo(any(RecordInfoDTO.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_RECORD_INFO")
    void getRecordInfoById_Success() throws Exception {
        when(recordInfoService.getRecordInfoByRecordInfoId("record1")).thenReturn(recordInfoDTO);

        mockMvc.perform(get("/api/v1/record-info/record1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched record info"))
                .andExpect(jsonPath("$.data.recordInfoId").value("record1"));

        verify(recordInfoService).getRecordInfoByRecordInfoId("record1");
    }

    @Test
    @WithMockUser(authorities = "VIEW_RECORD_INFO")
    void getRecordInfoById_NotFound_Fails() throws Exception {
        when(recordInfoService.getRecordInfoByRecordInfoId("invalidId"))
                .thenThrow(new RuntimeException("Record not found"));

        mockMvc.perform(get("/api/v1/record-info/invalidId"))
                .andExpect(status().is5xxServerError());

        verify(recordInfoService).getRecordInfoByRecordInfoId("invalidId");
    }

    @Test
    @WithMockUser(authorities = "VIEW_RECORD_INFO")
    void getAllRecordInfoByEvidenceId_Success() throws Exception {
        Page<RecordInfoDTO> page = new PageImpl<>(
                Collections.singletonList(recordInfoDTO),
                PageRequest.of(0, 10),
                1
        );
        when(recordInfoService.getRecordInfoByEvidenceId(eq("evidence1"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/record-info")
                        .param("evidenceId", "evidence1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched all record info"))
                .andExpect(jsonPath("$.data.content[0].recordInfoId").value("record1"));

        verify(recordInfoService).getRecordInfoByEvidenceId(eq("evidence1"), any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_RECORD_INFO")
    void getAllRecordInfoByEvidenceId_EmptyResult_Success() throws Exception {
        Page<RecordInfoDTO> emptyPage = new PageImpl<>(
                Collections.emptyList(),
                PageRequest.of(0, 10),
                0
        );
        when(recordInfoService.getRecordInfoByEvidenceId(eq("evidence1"), any(Pageable.class))).thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/record-info")
                        .param("evidenceId", "evidence1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetched all record info"))
                .andExpect(jsonPath("$.data.content").isEmpty());

        verify(recordInfoService).getRecordInfoByEvidenceId(eq("evidence1"), any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_RECORD_INFO")
    void updateRecordInfo_Success() throws Exception {
        when(recordInfoService.updateRecordInfo(eq("record1"), any(RecordInfoDTO.class))).thenReturn(recordInfoDTO);

        mockMvc.perform(put("/api/v1/record-info/record1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordInfoDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Record info updated"))
                .andExpect(jsonPath("$.data.recordInfoId").value("record1"));

        verify(recordInfoService).updateRecordInfo(eq("record1"), any(RecordInfoDTO.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_RECORD_INFO")
    void deleteRecordInfo_Success() throws Exception {
        doNothing().when(recordInfoService).deleteRecordInfoByRecordInfoId("record1");

        mockMvc.perform(delete("/api/v1/record-info/record1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Record info deleted"));

        verify(recordInfoService).deleteRecordInfoByRecordInfoId("record1");
    }
}