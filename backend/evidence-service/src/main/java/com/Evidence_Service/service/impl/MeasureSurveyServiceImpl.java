package com.Evidence_Service.service.impl;

import com.Evidence_Service.dto.MeasureSurveyDTO;
import com.Evidence_Service.exception.AppException;
import com.Evidence_Service.exception.ErrorCode;
import com.Evidence_Service.mapper.MeasureSurveyMapper;
import com.Evidence_Service.entity.MeasureSurvey;
import com.Evidence_Service.repository.MeasureSurveyRepository;
import com.Evidence_Service.service.EvidenceService;
import com.Evidence_Service.service.MeasureSurveyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasureSurveyServiceImpl implements MeasureSurveyService {

    private static final Logger log = LoggerFactory.getLogger(MeasureSurveyServiceImpl.class);

    private final MeasureSurveyRepository measureSurveyRepository;
    private final MeasureSurveyMapper measureSurveyMapper;
    private final EvidenceService evidenceService;

    @Override
    @Transactional
    @CacheEvict(value = {"measureSurvey", "measureSurveyByEvidence"}, allEntries = true)
    public MeasureSurveyDTO createMeasureSurvey(MeasureSurveyDTO dto) {
        try {
            MeasureSurvey entity = measureSurveyMapper.toEntity(dto);
            return measureSurveyMapper.toDTO(measureSurveyRepository.save(entity));
        } catch (Exception e) {
            log.error("Error while creating MeasureSurvey", e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to create MeasureSurvey");
        }
    }

    @Override
    @Cacheable(value = "measureSurvey", key = "'all_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<MeasureSurveyDTO> getAllMeasureSurvey(Pageable pageable) {
        try {
            return measureSurveyRepository.findAllByIsDeletedFalse(pageable)
                    .map(measureSurveyMapper::toDTO);
        } catch (Exception e) {
            log.error("Error while fetching all MeasureSurveys", e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to retrieve MeasureSurveys");
        }
    }


    @Override
    @Cacheable(value = "measureSurvey", key = "#measureSurveyId")
    public MeasureSurveyDTO getMeasureSurveyByMeasureSurveyId(String measureSurveyId) {
        try {
            MeasureSurvey entity = measureSurveyRepository.findById(measureSurveyId)
                    .orElseThrow(() -> new AppException(ErrorCode.MEASURE_SURVEY_NOT_FOUND));
            return measureSurveyMapper.toDTO(entity);
        } catch (AppException e) {
            throw e; // keep domain-specific exception
        } catch (Exception e) {
            log.error("Error while retrieving MeasureSurvey with ID: {}", measureSurveyId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to get MeasureSurvey by ID");
        }
    }


    @Override
    @Transactional
    @CacheEvict(value = {"measureSurvey", "measureSurveyByEvidence"}, allEntries = true)
    public MeasureSurveyDTO updateMeasureSurvey(String measureSurveyId, MeasureSurveyDTO dto) {
        try {
            MeasureSurvey entity = measureSurveyRepository.findById(measureSurveyId)
                    .orElseThrow(() -> new AppException(ErrorCode.MEASURE_SURVEY_NOT_FOUND));

            entity.setResultId(dto.getResultId());
            entity.setTypeName(dto.getTypeName());
            entity.setSource(dto.getSource());

            return measureSurveyMapper.toDTO(measureSurveyRepository.save(entity));
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while updating MeasureSurvey with ID: {}", measureSurveyId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to update MeasureSurvey");
        }
    }

    /**
     * Soft-delete a MeasureSurvey entity by setting the isDeleted flag to true.
     * Also soft-deletes all Evidence entities related to this MeasureSurvey.
     */
    @Override
    @Transactional
    @CacheEvict(value = {"measureSurvey", "measureSurveyByEvidence"}, allEntries = true)
    public void deleteMeasureSurveyByMeasureSurveyId(String measureSurveyId) {
        try {
            MeasureSurvey measureSurvey = measureSurveyRepository.findByMeasureSurveyIdAndIsDeletedFalse(measureSurveyId);
            if (measureSurvey == null) {
                throw new AppException(ErrorCode.MEASURE_SURVEY_NOT_FOUND);
            }

            // Soft-delete the MeasureSurvey
            measureSurvey.setDeleted(true);
            measureSurveyRepository.save(measureSurvey);

            // Cascade soft-delete all related Evidences
            evidenceService.softDeleteByMeasureSurveyId(measureSurveyId);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while deleting MeasureSurvey with ID: {}", measureSurveyId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to delete MeasureSurvey");
        }
    }
}
