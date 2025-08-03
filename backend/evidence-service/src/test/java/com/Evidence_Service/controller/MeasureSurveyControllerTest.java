package com.Evidence_Service.controller;

import com.Evidence_Service.dto.MeasureSurveyDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.MeasureSurveyService;
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

@WebMvcTest(MeasureSurveyController.class)
class MeasureSurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasureSurveyService measureSurveyService;

    @Autowired
    private ObjectMapper objectMapper;

    private MeasureSurveyDTO measureSurveyDTO;

    @BeforeEach
    void setUp() {
        objectMapper.addMixIn(PageImpl.class, PageImplMixin.class);

        measureSurveyDTO = MeasureSurveyDTO.builder()
                .measureSurveyId("survey1")
                .source("Test source")
                .typeName("Test type")
                .resultId("result1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }

    @JsonIgnoreProperties({"pageable"})
    private interface PageImplMixin {
    }

    @Test
    @WithMockUser(authorities = "ADD_MEASURE_SURVEY")
    void createMeasureSurvey_Success() throws Exception {
        when(measureSurveyService.createMeasureSurvey(any(MeasureSurveyDTO.class))).thenReturn(measureSurveyDTO);

        mockMvc.perform(post("/api/v1/measure-surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measureSurveyDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Created measure survey"))
                .andExpect(jsonPath("$.data.measureSurveyId").value("survey1"));

        verify(measureSurveyService).createMeasureSurvey(any(MeasureSurveyDTO.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_MEASURE_SURVEY")
    void getAllMeasureSurveys_Success() throws Exception {
        Page<MeasureSurveyDTO> page = new PageImpl<>(
                Collections.singletonList(measureSurveyDTO),
                PageRequest.of(0, 10),
                1
        );
        when(measureSurveyService.getAllMeasureSurvey(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/measure-surveys")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("List all measure surveys"))
                .andExpect(jsonPath("$.data.content[0].measureSurveyId").value("survey1"));

        verify(measureSurveyService).getAllMeasureSurvey(any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = "VIEW_MEASURE_SURVEY")
    void getMeasureSurveyById_Success() throws Exception {
        when(measureSurveyService.getMeasureSurveyByMeasureSurveyId("survey1")).thenReturn(measureSurveyDTO);

        mockMvc.perform(get("/api/v1/measure-surveys/survey1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Measure survey found"))
                .andExpect(jsonPath("$.data.measureSurveyId").value("survey1"));

        verify(measureSurveyService).getMeasureSurveyByMeasureSurveyId("survey1");
    }

    @Test
    @WithMockUser(authorities = "EDIT_MEASURE_SURVEY")
    void updateMeasureSurvey_Success() throws Exception {
        when(measureSurveyService.updateMeasureSurvey(eq("survey1"), any(MeasureSurveyDTO.class)))
                .thenReturn(measureSurveyDTO);

        mockMvc.perform(put("/api/v1/measure-surveys/survey1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measureSurveyDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Updated measure survey"))
                .andExpect(jsonPath("$.data.measureSurveyId").value("survey1"));

        verify(measureSurveyService).updateMeasureSurvey(eq("survey1"), any(MeasureSurveyDTO.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_MEASURE_SURVEY")
    void deleteMeasureSurvey_Success() throws Exception {
        doNothing().when(measureSurveyService).deleteMeasureSurveyByMeasureSurveyId("survey1");

        mockMvc.perform(delete("/api/v1/measure-surveys/survey1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Deleted measure survey"));

        verify(measureSurveyService).deleteMeasureSurveyByMeasureSurveyId("survey1");
    }
}