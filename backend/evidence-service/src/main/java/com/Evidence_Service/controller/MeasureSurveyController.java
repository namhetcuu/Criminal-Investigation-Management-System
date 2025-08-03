package com.Evidence_Service.controller;

import com.Evidence_Service.dto.MeasureSurveyDTO;
import com.Evidence_Service.dto.response.ApiResponse;
import com.Evidence_Service.service.MeasureSurveyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/measure-surveys")
@RequiredArgsConstructor
@Tag(name = "Measure Survey", description = "Management of measure surveys")
public class MeasureSurveyController {

    private final MeasureSurveyService measureSurveyService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_MEASURE_SURVEY')")
    public ApiResponse<MeasureSurveyDTO> create(@Valid  @RequestBody MeasureSurveyDTO dto) {
        return ApiResponse.<MeasureSurveyDTO>builder()
                .code(200)
                .message("Created measure survey")
                .data(measureSurveyService.createMeasureSurvey(dto))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_MEASURE_SURVEY')")
    public ApiResponse<Page<MeasureSurveyDTO>> getAll(@Valid @ParameterObject Pageable pageable) {
        return ApiResponse.<Page<MeasureSurveyDTO>>builder()
                .code(200)
                .message("List all measure surveys")
                .data(measureSurveyService.getAllMeasureSurvey(pageable))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_MEASURE_SURVEY')")
    public ApiResponse<MeasureSurveyDTO> getById(@Valid @PathVariable String id) {
        return ApiResponse.<MeasureSurveyDTO>builder()
                .code(200)
                .message("Measure survey found")
                .data(measureSurveyService.getMeasureSurveyByMeasureSurveyId(id))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_MEASURE_SURVEY')")
    public ApiResponse<MeasureSurveyDTO> update(@Valid @PathVariable String id, @RequestBody MeasureSurveyDTO dto) {
        return ApiResponse.<MeasureSurveyDTO>builder()
                .code(200)
                .message("Updated measure survey")
                .data(measureSurveyService.updateMeasureSurvey(id, dto))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_MEASURE_SURVEY')")
    public ApiResponse<Void> delete(@Valid @PathVariable String id) {
        measureSurveyService.deleteMeasureSurveyByMeasureSurveyId(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Deleted measure survey")
                .build();
    }
}
